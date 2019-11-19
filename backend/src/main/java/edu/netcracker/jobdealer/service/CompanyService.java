package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.AccountIdExistsException;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    List<Company> getAllCompanies();

    Company getCompanyById(UUID id);

    Company addCompany(String name, Boolean isVerified, String description, String avatarUrl, UUID accountId)
            throws AccountNotFoundException, AccountIdExistsException;
    //TODO исправить
//    Company updateCompany(UUID id, String name, Boolean isVerified, String description, String avatarUrl,
//                          UUID accountId);


    //читать импл
//    void deleteCompany(UUID id) throws CompanyNotFoundException;
    Company getByAccount(Account accountByEmail)throws CompanyNotFoundException;

    Company getByAccountEmail(String email) throws CompanyNotFoundException;
}
