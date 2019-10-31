package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.CompanyDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    List<CompanyDto> getAllCompanies();

    CompanyDto getCompanyById(UUID id);

    Company getByAccount(Account account) throws CompanyNotFoundException;

}
