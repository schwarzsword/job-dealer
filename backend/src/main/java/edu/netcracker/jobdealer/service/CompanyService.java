package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Company;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    List<Company> getAllCompanies();
    Company getCompanyById(UUID id);
}
