package edu.netcracker.jobdealer.service;


import edu.netcracker.jobdealer.dto.Filters;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.BadParameterException;
import edu.netcracker.jobdealer.exceptions.SkillNotFoundException;

import java.util.List;
import java.util.UUID;

public interface VacancyService {


    List<Vacancy> getAll();

    List<Vacancy> getVacanciesByCompany(Company company);

    Vacancy addOrUpdateVacancy(String name, String description,
                               Integer money, List<String> skills, String email,
                               String id);

    void remove(UUID vacancyId, String email);

    List<Vacancy> getVacanciesByCompanyEmail(String email);

    List<Vacancy> getPage(List<Vacancy> inp, int offset, int limit);

    public List<Vacancy> applyConditions(Filters filters);

    public int getSize(String filters);

    public List<Vacancy> sortAndReturn(String filters)
            throws SkillNotFoundException, BadParameterException;

}
