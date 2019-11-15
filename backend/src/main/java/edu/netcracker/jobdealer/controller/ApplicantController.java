package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ApplicantDto;
import edu.netcracker.jobdealer.service.ApplicantService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ApplicantController {

    private final ApplicantService applicantService;
    private final Mapper mapper;

    @Autowired
    public ApplicantController(ApplicantService applicantService, Mapper mapper) {
        this.applicantService = applicantService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/applicants")
    public List<ApplicantDto> getAllApplicants() {
        return applicantService.getAllApplicants().stream()
                .map(applicant -> mapper.map(applicant, ApplicantDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/applicants/{id}")
    public ApplicantDto getApplicantById(@PathVariable("id") UUID id) {
        return mapper.map(applicantService.getApplicantById(id), ApplicantDto.class);
    }
}
