package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping(value = "/resumes")
    public List<ResumeDto> getAllResumes() {
        return resumeService.getAllResumes();
    }

    @GetMapping(value = "/resumes/{id}")
    public ResumeDto getResumeById(@PathVariable UUID id) {
        return resumeService.getResumeById(id);
    }
}
