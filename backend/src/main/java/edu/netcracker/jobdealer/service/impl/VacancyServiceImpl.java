package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.dto.Filters;
import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.*;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.repository.*;
import edu.netcracker.jobdealer.service.JsonService;
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
    private final ApplicantRepository applicantRepository;
    private final JsonService jsonService;
    private final ResponseRepository responseRepository;

    @Autowired
    public VacancyServiceImpl(VacancyRepository vacancyRepository,
                              SkillsRepository skillsRepository,
                              CompanyRepository companyRepository,
                              ApplicantRepository applicantRepository, JsonService jsonService, ResponseRepository responseRepository) {
        this.vacancyRepository = vacancyRepository;
        this.skillsRepository = skillsRepository;
        this.companyRepository = companyRepository;
        this.applicantRepository = applicantRepository;
        this.jsonService = jsonService;
        this.responseRepository = responseRepository;
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
    public List<Vacancy> applyConditions(Filters filters) {
        if (filters.getRequestedSkills().size() == 0
                && filters.getVacancyName().equals("")
                && filters.getCompanyName().equals("")
                && filters.getMoney() < 0
        )
            throw new BadParameterException("Can't search with empty parameters");
        Set<Vacancy> vacancies = new HashSet<Vacancy>(vacancyRepository.findAll());
        Set<Vacancy> allByMoneyIsGreaterThanEqual = vacancyRepository
                .findDistinctByMoneyIsGreaterThanEqual(filters.getMoney());
        vacancies.retainAll(allByMoneyIsGreaterThanEqual);
        if (filters.getRequestedSkills().size() != 0) {
            List<Skills> requestedSkills = filters.getRequestedSkills().stream()
                    .map(e -> skillsRepository.findByName(e)
                            .orElseThrow(SkillNotFoundException::new))
                    .collect(Collectors.toList());
            requestedSkills.forEach(e -> {
                Set<Vacancy> allByRequestedSkillsContains = vacancyRepository.findDistinctByRequestedSkillsContains(e);
                vacancies.retainAll(allByRequestedSkillsContains);
            });
        }
        if (!filters.getVacancyName().equals("")) {
            Set<Vacancy> allByNameLike = vacancyRepository.findDistinctByNameContains(filters.getVacancyName());
            vacancies.retainAll(allByNameLike);
        }
        if (!filters.getCompanyName().equals("")) {
            Company company = companyRepository.findFirstByName(filters.getCompanyName())
                    .orElseThrow(CompanyNotFoundException::new);
            Set<Vacancy> allByNameLike = vacancyRepository.findDistinctByOwner(company);
            vacancies.retainAll(allByNameLike);
        }
        return new ArrayList<Vacancy>(vacancies);
    }

    @Override
    public List<Vacancy> sortAndReturn(String filtersStr) {
        Filters filters = jsonService.parseFilters(filtersStr);
        List<Vacancy> vacancies = applyConditions(filters);
        switch (filters.getSortBy()) {
            case "Salary":
                vacancies.sort(Comparator.comparing(Vacancy::getMoney));
                break;
            case "Company name":
                vacancies.sort(Comparator.comparing(Vacancy::getOwnerName));
                break;
            default:
                vacancies.sort(Comparator.comparing(Vacancy::getName));
        }
        if (filters.isDescending()) {
            Collections.reverse(vacancies);
        }
        return getPage(vacancies, filters.getOffset(), filters.getLimit());

    }

    @Override
    public Vacancy getVacancy(UUID vacancyId) {
        return vacancyRepository.findById(vacancyId).orElseThrow(VacancyNotFoundException::new);
    }


    @Override
    public int getSize(String filtersStr) {
        Filters filters = jsonService.parseFilters(filtersStr);
        return applyConditions(filters).size();
    }


    @Override
    public Vacancy addOrUpdateVacancy(String vacancyData, String email) {
        VacancyDto vacancyDto = jsonService.parseVacancyDto(vacancyData);
        Company company = companyRepository.findByAccountEmail(email).orElseThrow(CompanyNotFoundException::new);
        List<Skills> requestedSkills = vacancyDto.getRequestedSkills().stream()
                .map(e -> skillsRepository.findByName(e)
                        .orElseGet(() -> skillsRepository
                                .save(new Skills(e))))
                .collect(Collectors.toList());
        if (vacancyDto.getId() == null) {
            return vacancyRepository.save(new Vacancy(requestedSkills, vacancyDto.getName(), vacancyDto.getDescription(), vacancyDto.getMoney(), vacancyDto.isWithTask(), company));
        } else {
            Vacancy vacancy = vacancyRepository.findById(vacancyDto.getId()).orElseThrow(VacancyNotFoundException::new);
            vacancy.setName(vacancyDto.getName());
            vacancy.setOwner(company);
            vacancy.setDescription(vacancyDto.getDescription());
            vacancy.setMoney(vacancyDto.getMoney());
            vacancy.setWithTask(vacancyDto.isWithTask());
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
