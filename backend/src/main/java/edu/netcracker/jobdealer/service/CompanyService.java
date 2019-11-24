package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountIdExistsException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CompanyService {

    List<Company> getAllCompanies();

    Company getCompanyById(UUID id);

    Company addCompany(String name, Boolean isVerified, String description, byte[] fileData, UUID accountId)
            throws AccountByIdNotFoundException, AccountIdExistsException, IOException;

    Company updateCompany(UUID id, String name, Boolean isVerified, String description, byte[] fileData,
                          UUID accountId);

    Company getByAccount(Account accountByEmail);

    List<Company> getCompanies(Integer page, Integer size, String sortBy);

    Company getByAccountEmail(String email);
}

