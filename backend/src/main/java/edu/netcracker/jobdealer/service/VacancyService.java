package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoRightsException;
import edu.netcracker.jobdealer.exceptions.VacancyNotFoundException;

import java.util.List;
import java.util.UUID;

public interface VacancyService {

    List<Vacancy> getAll();

    List<Vacancy> getVacanciesByCompany(Company company);

    public void addVacancy(String name, String description, Integer money, List<String> skills, Company company) throws CompanyNotFoundException;

    void remove(UUID vacancyId, Company company) throws VacancyNotFoundException, NoRightsException;

    List<Vacancy> getVacanciesByCompanyEmail(String email);
}
