package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.CompanyDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.service.CompanyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final Mapper mapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, Mapper mapper) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(company -> this.mapper.map(company, CompanyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto getCompanyById(Integer id) {
        Company companyEntity = companyRepository.getOne(id);
        CompanyDto companyDto = mapper.map(companyEntity, CompanyDto.class);
        return mapper.map(companyDto, CompanyDto.class);
    }

    @Override
    public Company getByAccount(Account account) throws CompanyNotFoundException {
        Optional<Company> byAccount = companyRepository.findByAccount(account);
        if(byAccount.isPresent()){
            return byAccount.get();
        }else throw new CompanyNotFoundException("Company not found");
    }
}
