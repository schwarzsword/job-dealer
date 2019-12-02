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

    Vacancy addOrUpdateVacancy(String vacancyData, String email);

    void remove(UUID vacancyId, String email);

    List<Vacancy> getVacanciesByCompanyEmail(String email);

    List<Vacancy> getPage(List<Vacancy> inp, int offset, int limit);

    List<Vacancy> applyConditions(Filters filters);

    int getSize(String filters);

    List<Vacancy> sortAndReturn(String filters);

    Vacancy getVacancy(UUID vacancyId);


}
