package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.repository.SkillsRepository;
import edu.netcracker.jobdealer.repository.VacancyRepository;
import edu.netcracker.jobdealer.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class VacancyServiceImpl implements VacancyService {


    private final VacancyRepository vacancyRepository;
    private final SkillsRepository skillsRepository;
    private final CompanyRepository companyRepository;


    @Autowired
    public VacancyServiceImpl(final VacancyRepository vacancyRepository, SkillsRepository skillsRepository, CompanyRepository companyRepository) {
        this.vacancyRepository = vacancyRepository;
        this.skillsRepository = skillsRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Vacancy> getAll() {
        return vacancyRepository.findAll();
    }

    @Override
    public List<Vacancy> getVacanciesByCompany(Company company) {
        return vacancyRepository.findAllByOwner(company);
    }

    @Override
    public List<Vacancy> getVacanciesByCompanyEmail(String email) {
        return vacancyRepository.findAllByOwner_Account_Email(email);
    }

    @Override
    public List<Vacancy> getPage(List<Vacancy> vacancies, int offset, int limit) {
        int size = vacancies.size();
        if (size > offset) {
            return vacancies.subList(offset, limit > (size - offset) ? size : (limit + offset));
        } else return null;
    }

    @Override
    public List<Vacancy> applyConditions(List<String> skills, Integer salary, String vacancyName, String companyName) throws SkillNotFoundException, BadParameterException {
        if (skills == null && salary == null && vacancyName == null && companyName == null)
            throw new BadParameterException("Can't search with empty parameters");
        Set<Vacancy> vacancies = new HashSet<Vacancy>(vacancyRepository.findAll());
        if (skills.size() != 0) {
            List<Skills> requestedSkills = skills.stream()
                    .map(e -> skillsRepository.findByName(e)
                            .orElseThrow(SkillNotFoundException::new))
                    .collect(Collectors.toList());
            requestedSkills.forEach(e -> {
                Set<Vacancy> allByRequestedSkillsContains = vacancyRepository.findDistinctByRequestedSkillsContains(e);
                vacancies.retainAll(allByRequestedSkillsContains);
            });
        }
        if (salary != null) {
            Set<Vacancy> allByMoneyIsGreaterThanEqual = vacancyRepository.findDistinctByMoneyIsGreaterThanEqual(salary);
            vacancies.retainAll(allByMoneyIsGreaterThanEqual);
        }
        if (vacancyName != null) {
            Set<Vacancy> allByNameLike = vacancyRepository.findDistinctByNameLike(vacancyName);
            vacancies.retainAll(allByNameLike);
        }
        if (companyName != null) {
            Company company = companyRepository.findFirstByName(companyName).orElseThrow(CompanyNotFoundException::new);
            Set<Vacancy> allByNameLike = vacancyRepository.findDistinctByOwner(company);
            vacancies.retainAll(allByNameLike);
        }
        return new ArrayList<Vacancy>(vacancies);
    }

    @Override
    public List<Vacancy> sortAndReturn(List<String> skills, Integer salary,
                                       String vacancyName, String companyName, int offset, int limit,
                                       String sortBy)
            throws SkillNotFoundException, BadParameterException {
        List<Vacancy> vacancies = applyConditions(skills, salary, vacancyName, companyName);
        switch (sortBy) {
            case "Vacancy name descending":
                vacancies.sort(Comparator.comparing(Vacancy::getName).reversed());
                break;
            case "Salary ascending":
                vacancies.sort(Comparator.comparing(Vacancy::getMoney));
                break;
            case "Salary descending":
                vacancies.sort(Comparator.comparing(Vacancy::getMoney).reversed());
                break;
            case "Company name ascending":
                vacancies.sort(Comparator.comparing(Vacancy::getOwnerName));
                break;
            case "Company name descending":
                vacancies.sort(Comparator.comparing(Vacancy::getOwnerName).reversed());
                break;
            default:
                vacancies.sort(Comparator.comparing(Vacancy::getName));
        }
        return getPage(vacancies, offset, limit);

    }

    @Override
    public int getSize(List<String> skills, Integer salary, String vacancyName, String companyName) {
        return applyConditions(skills, salary, vacancyName, companyName).size();
    }


    @Override
    public Vacancy addOrUpdateVacancy(String name, String description, Integer money,
                                      List<String> skills, String email, String id) throws CompanyNotFoundException {
        Company company = companyRepository.findByAccountEmail(email).orElseThrow(CompanyNotFoundException::new);
        List<Skills> requestedSkills = skills.stream()
                .map(e -> skillsRepository.findByName(e)
                        .orElseGet(() -> skillsRepository
                                .save(new Skills(e))))
                .collect(Collectors.toList());
        if (id.equals("null")) {
            return vacancyRepository.save(new Vacancy(name, description, money, requestedSkills, company));
        } else {
            Vacancy vacancy = vacancyRepository.findById(UUID.fromString(id)).orElseThrow(VacancyNotFoundException::new);
            vacancy.setName(name);
            vacancy.setOwner(company);
            vacancy.setDescription(description);
            vacancy.setMoney(money);
            vacancy.setRequestedSkills(requestedSkills);
            return vacancyRepository.save(vacancy);
        }
    }

    @Override
    public void remove(UUID vacancyId, String email) throws VacancyNotFoundException, NoPermissionException {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(VacancyNotFoundException::new);
        if (email.equals(vacancy.getOwner().getAccount().getEmail())) {
            vacancyRepository.delete(vacancy);
        } else throw new NoPermissionException();
    }


}
