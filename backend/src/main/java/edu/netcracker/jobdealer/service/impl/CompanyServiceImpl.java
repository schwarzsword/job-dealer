package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.CompanyDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.CompanyService;
import edu.netcracker.jobdealer.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static edu.netcracker.jobdealer.util.FileWorker.extractBytes;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final JsonService jsonService;

    @Value("${upload.path}")
    private String path;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, AccountRepository accountRepository,
                              AccountService accountService, JsonService jsonService) {
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.jsonService = jsonService;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getCompanies(int page, int limit, String sortBy) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(sortBy));
        Page<Company> pagedResult = companyRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            throw new CompanyNotFoundException("Companies are not found");
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
    public Company addCompany(String companyDataString) {
        CompanyDto companyData = jsonService.parseCompanyDto(companyDataString);
        if (!companyRepository.existsByAccount_Id(companyData.getAccountId())) {
            if (companyRepository.existsByName(companyData.getName())) {
                accountService.deleteAccount(companyData.getAccountId());
                throw new CompanyNameAlreadyInUseException();
            }
            Account byId = accountService.getById(companyData.getAccountId());
            try {
                if (companyData.getFileData() == null) {
                    companyData.setFileData(extractBytes(path));
                }
            } catch (IOException e) {
                throw new BadException();
            }
            return companyRepository.save(new Company(companyData.getName(), false, companyData.getDescription(), companyData.getFileData(), byId));
        } else throw new AccountIdExistsException();
    }

    @Override
    public Company updateCompany(UUID id, String name, Boolean isVerified, String description, String fileData, UUID accountId) {
        throw new NotImplementedMethodException("");
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

    @Override
    public List<String> getCompanyNames() {
        return companyRepository.findAll().stream().map(Company::getName).distinct().collect(Collectors.toList());
    }


}
