package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.CompanyDto;
import edu.netcracker.jobdealer.dto.Filters;
import edu.netcracker.jobdealer.dto.ResumeDto;

public interface JsonService {
    String toJson(Object o);

    Filters parseFilters(String s);

    ResumeDto parseResumeDto(String s);

    CompanyDto parseCompanyDto(String s);
}