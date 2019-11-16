package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.CompanyDto;
import edu.netcracker.jobdealer.exceptions.AccountIdExistsException;
import edu.netcracker.jobdealer.service.CompanyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
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
    public List<CompanyDto> getCompanies(@RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return companyService.getCompanies(page, size, sortBy).stream()
                .map(company -> this.mapper.map(company, CompanyDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/companies/{id}")
    public CompanyDto getCompanyById(@PathVariable("id") UUID id) {
        return mapper.map(companyService.getCompanyById(id), CompanyDto.class);
    }

    @PostMapping(value = "/companies")
    public ResponseEntity<?> addCompany(@RequestParam String name, @RequestParam Boolean isVerified,
                                 @RequestParam String description, @RequestParam String avatarUrl,
                                 @RequestParam UUID accountId) {
        try {
            CompanyDto company = mapper.map(
                    companyService.addCompany(name, isVerified, description, avatarUrl, accountId), CompanyDto.class);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (AccountNotFoundException | AccountIdExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/companies")
    public ResponseEntity<?> updateCompany(@RequestParam UUID id, @RequestParam String name,
                                           @RequestParam Boolean isVerified, @RequestParam String description,
                                           @RequestParam String avatarUrl,
                                           @RequestParam UUID accountId) {
        try {
            CompanyDto company = mapper.map(
                    companyService.updateCompany(id, name, isVerified, description, avatarUrl, accountId), CompanyDto.class);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (AccountIdExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
