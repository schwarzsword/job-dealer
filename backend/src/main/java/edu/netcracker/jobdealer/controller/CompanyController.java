package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.CompanyDto;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.service.CompanyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getCompanies(@RequestParam int page, @RequestParam int limit, @RequestParam String sortBy) {
        try {
            List<CompanyDto> companies = companyService.getCompanies(page, limit, sortBy).stream()
                    .map(company -> this.mapper.map(company, CompanyDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(companies);

        } catch (CompanyNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/names/companies")
    public ResponseEntity<?> getCompanies() {
        return ResponseEntity.ok(companyService.getCompanyNames());
    }

    @GetMapping(value = "/companies/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable("id") UUID id) {
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(mapper.map(company, CompanyDto.class));
    }

    @PostMapping(value = "/companies")
    public ResponseEntity<?> addCompany(@RequestParam String companyData) {
        CompanyDto company = mapper
                .map(companyService
                        .addCompany(companyData), CompanyDto.class);
        return ResponseEntity.ok(company);
    }

}
