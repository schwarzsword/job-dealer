package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.AccountIdExistsException;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    // TODO: убрать null  и посмотреть исключения (+)
    // TODO: потестить методы, хотя бы визуально

    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, AccountRepository accountRepository) {
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(UUID id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isPresent()) {
            return company.get();
        } else {
            throw new CompanyNotFoundException("Company is not found!");
        }
    }

    @Override
    public Company addCompany(String name, Boolean isVerified, String description, String avatarUrl, UUID accountId)
            throws AccountNotFoundException {
        if (!companyRepository.existsByAccount_Id(accountId)) {
            throw new AccountIdExistsException("Account ID held by another company");
        } else if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account is not found");
        } else {
            return companyRepository.save(new Company(name, isVerified, description, avatarUrl, accountId));
        }
    }

    @Override
    public Company updateCompany(UUID id, String name, Boolean isVerified, String description, String avatarUrl,
                                 UUID accountId) {
        if (companyRepository.existsByAccount_Id(accountId)) {
            return companyRepository.save(new Company(id, name, isVerified, description, avatarUrl, accountId));
        } else {
            throw new AccountIdExistsException("Account id is already exists");
        }
    }

    @Override
    public ResponseEntity deleteCompany(UUID id) throws CompanyNotFoundException {
        if (companyRepository.findById(id).isPresent() && id != null) {
            companyRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } else {
            throw new CompanyNotFoundException("You passed an empty parameter or the company was not found");
        }
    }

    @Override
    public Company getByAccount(Account accountByEmail) {
        Optional<Company> company = companyRepository.findByAccount(accountByEmail);
        if (company.isPresent()) {
            return company.get();
        } else {
            throw new CompanyNotFoundException("Company is not found");
        }
    }
}
