package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountIdExistsException;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.exceptions.NotImplementedMethodException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    // TODO: убрать null  и посмотреть исключения (+)
    // TODO: потестить методы, хотя бы визуально

    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Value("${upload.path}")
    private String path;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, AccountRepository accountRepository,
                              AccountService accountService) {
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
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
    public Company addCompany(String name, Boolean isVerified, String description, byte[] fileData, UUID accountId)
            throws AccountByIdNotFoundException, AccountIdExistsException, IOException {
        if (!companyRepository.existsByAccount_Id(accountId)) {
            Account byId = accountService.getById(accountId);
            byte[] n = "null".getBytes();
            if (Arrays.equals(n, fileData)) {
                fileData = extractBytes(path);
            }
            return companyRepository.save(new Company(name, false, description, fileData, byId));
        } else throw new AccountIdExistsException();
    }

    @Override
    public Company updateCompany(UUID id, String name, Boolean isVerified, String description, byte[] fileData, UUID accountId) {
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

    private byte[] extractBytes(String imageName) throws IOException {
        File imgPath = new File(imageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }
}
