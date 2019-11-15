package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(final ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    //@Secured("ROLE_USER")
    @GetMapping(value = "/{userId}/resumes/")
    public ResponseEntity<?> getAllResumes(@PathVariable("userId") @NotBlank @Valid UUID userId) {

        List<Resume> resumes = resumeService.getAllResumeOfUser(userId);
        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }

    //@Secured("ROLE_USER")
    @PatchMapping(value = "/{userId}/resumes/")
    public ResponseEntity<?> createResume(@PathVariable("userId") @NotBlank @Valid UUID userId,
                                          @RequestBody Resume resume) {
        Resume createdResume = resumeService.add(resume);
        return new ResponseEntity<>(createdResume, HttpStatus.OK);
    }

    //@Secured("ROLE_USER")
    @DeleteMapping(value = "/{userId}/resumes/{resumeId}")
    public ResponseEntity<?> removeResume(@PathVariable("userId") @NotBlank @Valid UUID userId,
                                          @PathVariable("resumeId") @NotBlank @Valid UUID resumeId) {
        resumeService.remove(resumeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //@Secured("ROLE_USER")
    @PatchMapping(value = "/{userId}/resumes/{resumeId}")
    public ResponseEntity<?> updateResume(@PathVariable("userId") @NotBlank @Valid UUID userId,
                                          @PathVariable("resumeId") @NotBlank @Valid UUID resumeId,
                                          @RequestBody Resume resume) {
        resumeService.update(resumeId, resume);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
