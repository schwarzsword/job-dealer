package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ResponseDto;
import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.ApplicantService;
import edu.netcracker.jobdealer.service.ResumeService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

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

    @Secured("ROLE_USER")
    @PostMapping(value = "/my/resumes")
    public ResponseEntity<?> createResume(@RequestParam String resumeData) {
        Resume add = resumeService.addOrUpdate(resumeData);
        return ResponseEntity.ok(mapper.map(add, ResumeDto.class));
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/my/resumes")
    public ResponseEntity<?> getOwnedResumes(@AuthenticationPrincipal User user) {
        List<Resume> ownedResumes = resumeService.getOwnedResumes(user.getUsername());
        return ResponseEntity.ok(ownedResumes.stream()
                .map(e -> mapper.map(e, ResponseDto.class)));
    }

    @Secured("ROLE_USER")
    @DeleteMapping(value = "/my/resumes/{id}")
    public ResponseEntity<?> deleteResume(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        resumeService.deleteResume(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/resumes/{id}")
    public ResponseEntity<?> getResume(@PathVariable UUID id) {
        Resume resume = resumeService.getResume(id);
        return ResponseEntity.ok(mapper.map(resume, ResumeDto.class));
    }

    //@Secured("ROLE_COMPANY")
    @GetMapping(value = "/resumes")
    public ResponseEntity<?> getVacancies(@RequestParam String filters) {
        List<Resume> vacancies = resumeService.sortAndReturn(filters);
        return ResponseEntity.ok(vacancies.stream()
                .map(e -> mapper.map(e, ResumeDto.class))
                .collect(Collectors.toList()));
    }

    //@Secured("ROLE_COMPANY")
    @GetMapping(value = "/resumes/size")
    public ResponseEntity<?> getSize(@RequestParam String filters) {
        return ResponseEntity.ok(resumeService.getSize(filters));
    }

}
