package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.CompanyDto;
import edu.netcracker.jobdealer.service.CompanyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CompanyController {

    private final Mapper mapper;
    private final CompanyService companyService;

    @Autowired
    public CompanyController(Mapper mapper, CompanyService companyService) {
        this.mapper = mapper;
        this.companyService = companyService;
    }

    @GetMapping(value = "/companies")
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies().stream()
                .map(company -> this.mapper.map(company, CompanyDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/companies/{id}")
    public CompanyDto getCompanyById(@PathVariable("id") UUID id) {
        return mapper.map(companyService.getCompanyById(id), CompanyDto.class);
    }
}
