package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.ApplicantService;
import edu.netcracker.jobdealer.service.ResumeService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ResumeController {

    private final ResumeService resumeService;
    private final AccountService accountService;
    private final ApplicantService applicantService;
    private final Mapper mapper;
    @Value("${upload.path}")
    private String path;

    public ResumeController(ResumeService resumeService,
                            AccountService accountService,
                            ApplicantService applicantService,
                            Mapper mapper) {
        this.resumeService = resumeService;
        this.accountService = accountService;
        this.applicantService = applicantService;
        this.mapper = mapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/my/resumes/")
    public ResponseEntity<?> getAllResumes(@AuthenticationPrincipal User user) {
        //todo сделать хорошо, пофиксить урлы, подумать о безопасности, использовать билдер
        List<Resume> userResumes = resumeService.getAllResumeOfUser(user.getUsername());
        List<ResumeDto> dtos = userResumes
                .stream()
                .map(e -> mapper.map(e, ResumeDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
//todo: implement
//    @PreAuthorize("isAuthenticated()")
//    @DeleteMapping(value = "/my/resumes/{resumeId}")
//    public ResponseEntity<?> removeResume(@PathVariable("resumeId") @Valid UUID resumeId) {
//        try {
//            resumeService.remove(resumeId);
//            return ResponseEntity.noContent().build();
//        } catch (ApplicantNotFoundException | NoPermissionException ex) {
//            return ResponseEntity.status(401).body("You have no permission to delete resumes");
//        }
//    }

    @Secured("ROLE_USER")
    @PatchMapping(value = "/my/resumes/{resumeId}")
    public ResponseEntity<?> updateResume(@RequestParam UUID userId,
                                          @PathVariable @Valid UUID resumeId,
                                          @RequestParam String resumeName,
                                          @RequestParam String firstName,
                                          @RequestParam String lastName,
                                          @RequestParam String about,
                                          @RequestParam String avatarUrl,
                                          @RequestParam int salary,
                                          @RequestParam List<Skills> skills,
                                          @RequestBody Resume resume) {
            Resume updatedResume = resumeService.update(resumeId, resumeName, firstName, lastName, about, avatarUrl, salary, skills);
            return ResponseEntity.ok(updatedResume);
    }

    @Secured("ROLE_USER")
    @PostMapping(value = "/my/resumes")
    public ResponseEntity<?> createResume(@RequestParam String resumeData) {
        try {
            Resume add = resumeService.add(resumeData);
            return ResponseEntity.ok(mapper.map(add, ResumeDto.class));
        } catch (ResumeAlreadyExistsException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ApplicantNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping(value = "/resumes")
    public ResponseEntity<?> getResumes(@RequestParam int offset,
                                        @RequestParam int limit,
                                        @RequestParam(required = false) String sortBy,
                                        @RequestParam(required = false) String country,
                                        @RequestParam(required = false) String city,
                                        @RequestParam(required = false) int salaryMin,
                                        @RequestParam(required = false) int salaryMax,
                                        @RequestParam(required = false) boolean experience,
                                        @RequestParam(required = false) boolean driverLicence) {
        try {
            List<Resume> resumes = resumeService.sortAndReturn(country, city, salaryMin, salaryMax, experience, driverLicence, offset, limit, sortBy);
            if (resumes != null) {
                return ResponseEntity.ok(resumes.stream()
                        .map(e -> mapper.map(e, ResumeDto.class))
                        .collect(Collectors.toList()));
            } else return ResponseEntity.noContent().build();
        } catch (ResumeNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (BadParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
