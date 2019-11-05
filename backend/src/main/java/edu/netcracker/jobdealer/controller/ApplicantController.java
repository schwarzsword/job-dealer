package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ApplicantDto;
import edu.netcracker.jobdealer.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping(value = "/applicants")
    public List<ApplicantDto> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @GetMapping(value = "/applicants/{id}")
    public ApplicantDto getApplicantById(@PathVariable("id") UUID id) {
        return applicantService.getApplicantById(id);
    }
}
