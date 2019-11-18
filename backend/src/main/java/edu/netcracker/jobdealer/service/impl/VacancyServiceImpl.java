package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.exceptions.SkillNotFoundException;
import edu.netcracker.jobdealer.exceptions.VacancyNotFoundException;
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


    @Autowired
    public VacancyServiceImpl(final VacancyRepository vacancyRepository, SkillsRepository skillsRepository) {
        this.vacancyRepository = vacancyRepository;
        this.skillsRepository = skillsRepository;
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
            return vacancies.subList(offset, limit > (size - offset) ? size : limit);
        } else return null;
    }

    @Override
    public List<Vacancy> applyConditions(String skill, Integer salary) throws SkillNotFoundException {
        Skills skills = skillsRepository.findByName(skill).orElseThrow(SkillNotFoundException::new);
        Set<Vacancy> allByRequestedSkillsContains = vacancyRepository.findAllByRequestedSkillsContains(skills);
        if (salary != null) {
            Set<Vacancy> allByMoneyIsGreaterThanEqual = vacancyRepository.findAllByMoneyIsGreaterThanEqual(salary);
            allByRequestedSkillsContains.retainAll(allByMoneyIsGreaterThanEqual);
        }
        return new ArrayList<Vacancy>(allByRequestedSkillsContains);
    }


    @Override
    public Vacancy addVacancy(String name, String description, Integer money,
                              List<String> skills, Company company) throws CompanyNotFoundException {
        List<Skills> requestedSkills = skills.stream()
                .map(e -> skillsRepository.findByName(e)
                        .orElseGet(() -> skillsRepository
                                .save(new Skills(e))))
                .collect(Collectors.toList());
        return vacancyRepository.save(new Vacancy(name, description, money, requestedSkills, company));
    }

    @Override
    public void remove(UUID vacancyId, Company company) throws VacancyNotFoundException, NoPermissionException {
        Optional<Vacancy> byId = vacancyRepository.findById(vacancyId);
        if (byId.isPresent()) {
            Vacancy vacancy = byId.get();
            if (vacancy.getOwner().equals(company)) {
                vacancyRepository.delete(vacancy);
            } else throw new NoPermissionException("You can't delete this vacancy");
        } else throw new VacancyNotFoundException("Vacancy not found");
    }


}
