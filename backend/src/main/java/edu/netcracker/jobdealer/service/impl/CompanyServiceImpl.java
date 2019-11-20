package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountIdExistsException;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;

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
    private final AccountService accountService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, AccountRepository accountRepository, AccountService accountService) {
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getCompanies(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Company> pagedResult = companyRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
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
            throws AccountByIdNotFoundException, AccountIdExistsException {
        if (!companyRepository.existsByAccount_Id(accountId)) {
            Account byId = accountService.getById(accountId);
            return companyRepository.save(new Company(name, false, description, avatarUrl, byId));
        } else throw new AccountIdExistsException();
    }

    //TODO исправить
//    @Override
//    public Company updateCompany(UUID id, String name, Boolean isVerified, String description, String avatarUrl,
//                                 UUID accountId) {
//        if (companyRepository.existsByAccount_Id(accountId)) {
//            return companyRepository.save(new Company(id, name, isVerified, description, avatarUrl, accountId));
//        } else {
//            throw new AccountIdExistsException("Account id is already exists");
//        }
//    }


    //TODO удаление должно быть на уровне аккаунта
//    @Override
//    public void deleteCompany(UUID id) throws CompanyNotFoundException {
//        Company company = companyRepository.findById().orElseThrow(CompanyNotFoundException::new);
//        companyRepository.de
//    }

    @Override
    public Company getByAccount(Account accountByEmail) throws CompanyNotFoundException {
        return companyRepository.findByAccount(accountByEmail).orElseThrow(CompanyNotFoundException::new);
    }

    @Override
    public Company getByAccountEmail(String email) throws CompanyNotFoundException {
        return companyRepository.findByAccountEmail(email).orElseThrow(CompanyNotFoundException::new);
    }
}
