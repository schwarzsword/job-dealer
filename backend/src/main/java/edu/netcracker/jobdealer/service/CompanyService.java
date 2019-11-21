package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    List<Company> getAllCompanies();

    Company getCompanyById(UUID id);

    Company addCompany(String name, Boolean isVerified, String description, String avatarUrl, UUID accountId)
            throws AccountNotFoundException;

    Company updateCompany(UUID id, String name, Boolean isVerified, String description, String avatarUrl,
                          UUID accountId);

    Company getByAccount(Account accountByEmail);

    List<Company> getCompanies(int page, int size, String sortBy);

    Company getByAccountEmail(String email);
}
