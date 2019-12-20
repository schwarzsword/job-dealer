package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ApplicantDto;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.service.ApplicantService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class ApplicantController {

    private final ApplicantService applicantService;
    private final Mapper mapper;

    @Autowired
    public ApplicantController(ApplicantService applicantService, Mapper mapper) {
        this.applicantService = applicantService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/applicants")
    public ResponseEntity<?> addApplicant(@RequestParam UUID accountId) {
        Applicant applicant = applicantService.addApplicant(accountId);
        return ResponseEntity.ok(mapper.map(applicant, ApplicantDto.class));
    }

    @GetMapping(value = "/applicants/me")
    @Secured("ROLE_USER")
    public ResponseEntity<?> who(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(applicantService.who(user.getUsername()).getId());
    }
}
