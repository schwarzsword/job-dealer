package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.VacancyNotFoundException;

import java.util.List;

public interface VacancyService {

    List<Vacancy> getAll();

    List<Vacancy> getVacanciesByCompany(Company company);

    Vacancy addVacancy(Vacancy vacancy);

    void remove(Long vacancyId) throws VacancyNotFoundException;

    Vacancy updateVacancy(VacancyDto vacancyDTO);
}
