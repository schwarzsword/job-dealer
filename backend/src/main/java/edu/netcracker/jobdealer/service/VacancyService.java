package edu.netcracker.jobdealer.service;


import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.*;

import java.util.List;
import java.util.UUID;

public interface VacancyService {


    List<Vacancy> getAll();

    List<Vacancy> getVacanciesByCompany(Company company);

    Vacancy addOrUpdateVacancy(String name, String description,
                               Integer money, List<String> skills, String email,
                               String id) throws CompanyNotFoundException;

    void remove(UUID vacancyId, String email) throws VacancyNotFoundException, NoPermissionException;

    List<Vacancy> getVacanciesByCompanyEmail(String email);

    List<Vacancy> getPage(List<Vacancy> inp, int offset, int limit);

    public List<Vacancy> applyConditions(List<String> skills, Integer salary, String vacancyName, String companyName) throws SkillNotFoundException;

    public int getSize(List<String> skills, Integer salary, String vacancyName, String companyName);

    public List<Vacancy> sortAndReturn(List<String> skills, Integer salary,
                                       String vacancyName, String companyName,
                                       int offset, int limit,
                                       String sortBy)
            throws SkillNotFoundException, BadParameterException;

}
