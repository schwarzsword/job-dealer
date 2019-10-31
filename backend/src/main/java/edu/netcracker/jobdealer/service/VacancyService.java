package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.VacancyNotFoundException;

import java.util.List;
import java.util.UUID;

public interface VacancyService {

    List<Vacancy> getAll();

    List<Vacancy> getVacanciesByCompany(Company company);

    Vacancy addVacancy(Vacancy vacancy);

    void remove(UUID vacancyId) throws VacancyNotFoundException;

    Vacancy updateVacancy(VacancyDto vacancyDTO);
}
