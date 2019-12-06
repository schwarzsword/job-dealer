package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.dto.VacancyFilters;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.entity.Vacancy;
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
        } else return Collections.emptyList();
    }

    @Override
    public List<Vacancy> applyConditions(VacancyFilters vacancyFilters) {
        if (vacancyFilters.getRequestedSkills().size() == 0
                && vacancyFilters.getVacancyName().equals("")
                && vacancyFilters.getCompanyName().equals("")
                && vacancyFilters.getMoney() < 0
        )
            throw new BadParameterException("Can't search with empty parameters");
        Set<Vacancy> vacancies = new HashSet<Vacancy>(vacancyRepository.findAll());
        Set<Vacancy> allByMoneyIsGreaterThanEqual = vacancyRepository
                .findDistinctByMoneyIsGreaterThanEqual(vacancyFilters.getMoney());
        vacancies.retainAll(allByMoneyIsGreaterThanEqual);
        if (vacancyFilters.getRequestedSkills().size() != 0) {
            List<Skills> requestedSkills = vacancyFilters.getRequestedSkills().stream()
                    .map(e -> skillsRepository.findByName(e)
                            .orElseThrow(SkillNotFoundException::new))
                    .collect(Collectors.toList());
            requestedSkills.forEach(e -> {
                Set<Vacancy> allByRequestedSkillsContains = vacancyRepository.findDistinctByRequestedSkillsContains(e);
                vacancies.retainAll(allByRequestedSkillsContains);
            });
        }
        if (!vacancyFilters.getVacancyName().equals("")) {
            Set<Vacancy> allByNameLike = vacancyRepository.findDistinctByNameContains(vacancyFilters.getVacancyName());
            vacancies.retainAll(allByNameLike);
        }
        if (!vacancyFilters.getCompanyName().equals("")) {
            Company company = companyRepository.findFirstByName(vacancyFilters.getCompanyName())
                    .orElseThrow(CompanyNotFoundException::new);
            Set<Vacancy> allByNameLike = vacancyRepository.findDistinctByOwner(company);
            vacancies.retainAll(allByNameLike);
        }
        return new ArrayList<>(vacancies);
    }

    @Override
    public List<Vacancy> sortAndReturn(String filtersStr) {
        VacancyFilters vacancyFilters = jsonService.parseVacancyFilters(filtersStr);
        List<Vacancy> vacancies = applyConditions(vacancyFilters);
        switch (vacancyFilters.getSortBy()) {
            case "Salary":
                vacancies.sort(Comparator.comparing(Vacancy::getMoney));
                break;
            case "Company name":
                vacancies.sort(Comparator.comparing(Vacancy::getOwnerName));
                break;
            default:
                vacancies.sort(Comparator.comparing(Vacancy::getName));
        }
        if (vacancyFilters.isDescending()) {
            Collections.reverse(vacancies);
        }
        return getPage(vacancies, vacancyFilters.getOffset(), vacancyFilters.getLimit());

    }

    @Override
    public Vacancy getVacancy(UUID vacancyId) {
        return vacancyRepository.findById(vacancyId).orElseThrow(VacancyNotFoundException::new);
    }


    @Override
    public int getSize(String filtersStr) {
        VacancyFilters vacancyFilters = jsonService.parseVacancyFilters(filtersStr);
        return applyConditions(vacancyFilters).size();
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
        Vacancy vacancy = new Vacancy(requestedSkills, vacancyDto.getName(), vacancyDto.getDescription(), vacancyDto.getMoney(), vacancyDto.isWithTask(), company);
        if (vacancyDto.getId() != null) {
            vacancy.setId(vacancyDto.getId());
        }
        return vacancyRepository.save(vacancy);

    }

    @Override
    public void remove(UUID vacancyId, String email) throws VacancyNotFoundException, NoPermissionException {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(VacancyNotFoundException::new);
        if (email.equals(vacancy.getOwner().getAccount().getEmail())) {
            vacancyRepository.delete(vacancy);
        } else throw new NoPermissionException();
    }


}
