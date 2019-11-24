package edu.netcracker.jobdealer.service;


import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.*;

import java.util.List;
import java.util.UUID;

public interface VacancyService {


    List<Vacancy> getAll();

    List<Vacancy> getVacanciesByCompany(Company company);

    Vacancy addVacancy(String name, String description, Integer money, List<String> skills, Company company) throws CompanyNotFoundException;

    Vacancy addVacancy(String name, String description, Integer money, List<String> skills, String email) throws CompanyNotFoundException;

    void remove(UUID vacancyId, Company company) throws VacancyNotFoundException, NoPermissionException;

    List<Vacancy> getVacanciesByCompanyEmail(String email);

    List<Vacancy> getPage(List<Vacancy> inp, int offset, int limit);

    public List<Vacancy> applyConditions(List<String> skills, Integer salary, String resumeName) throws SkillNotFoundException;

    public int getSize();

    public List<Vacancy> sortAndReturn(List<String> skills, Integer salary,
                                       String resumeName, int offset, int limit,
                                       Integer sortBy, Boolean sortAsc)
            throws SkillNotFoundException, BadParameterException;

}
