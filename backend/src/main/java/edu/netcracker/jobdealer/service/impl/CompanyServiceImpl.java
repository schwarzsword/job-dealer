package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.AccountIdAlreadyExistsException;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
    public Company addCompany(String name, Boolean isVerified, String description, String avatarUrl, UUID accountId) {
        if (!companyRepository.existsByAccount_Id(accountId)) {
            return companyRepository.save(new Company(name, isVerified, description, avatarUrl, accountId));
        } else {
            throw new AccountIdAlreadyExistsException("Account id is already exists");
        }
    }

    @Override
    public Company updateCompany(UUID id, String name, Boolean isVerified, String description, String avatarUrl,
                                 UUID accountId) {

        if (!companyRepository.existsByAccount_Id(accountId)) {
            return companyRepository.save(new Company(id, name, isVerified, description, avatarUrl, accountId));
        } else {
            throw new AccountIdAlreadyExistsException("Account id is already exists");
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
}
