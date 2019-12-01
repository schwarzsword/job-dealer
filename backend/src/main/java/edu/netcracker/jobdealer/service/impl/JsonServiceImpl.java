package edu.netcracker.jobdealer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import edu.netcracker.jobdealer.dto.CompanyDto;
import edu.netcracker.jobdealer.dto.Filters;
import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.service.JsonService;
import org.springframework.stereotype.Service;

@Service
public class JsonServiceImpl implements JsonService {
    @Override
    public String toJson(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public Filters parseFilters(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        try {
            return objectMapper.readValue(s, Filters.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public ResumeDto parseResumeDto(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        try {
            return objectMapper.readValue(s, ResumeDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CompanyDto parseCompanyDto(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        try {
            return objectMapper.readValue(s, CompanyDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}