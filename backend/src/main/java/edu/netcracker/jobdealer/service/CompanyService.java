package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface CompanyService {

    List<Company> getAllCompanies();
    Company getCompanyById(UUID id);
    Company addCompany(String name, Boolean isVerified, String description, String avatarUrl, UUID accountId)
            throws AccountNotFoundException;
    Company updateCompany(UUID id, String name, Boolean isVerified, String description, String avatarUrl,
                          UUID accountId);
    ResponseEntity deleteCompany(UUID id) throws CompanyNotFoundException;
    Company getByAccount(Account accountByEmail);
    List<Company> getCompanies(Integer page, Integer size, String sortBy);
    }
