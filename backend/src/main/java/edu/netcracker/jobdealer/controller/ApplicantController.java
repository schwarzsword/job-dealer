package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ApplicantDto;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.exceptions.AccountAlreadyInUseException;
import edu.netcracker.jobdealer.service.ApplicantService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;
    private final Mapper mapper;

    @Autowired
    public ApplicantController(ApplicantService applicantService, Mapper mapper) {
        this.applicantService = applicantService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> addApplicant(@RequestParam UUID accountId) {
        try {
            Applicant applicant = applicantService.addApplicant(accountId);
            return ResponseEntity.ok(mapper.map(applicant, ApplicantDto.class));
        } catch (AccountAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
