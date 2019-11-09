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

@RestController
@RequestMapping("api/user")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(final ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping(value = "/{login}/resumes/")
    public ResponseEntity<?> getAllResumes(@PathVariable("login") @NotBlank @Valid String login) {

        List<Resume> resumes = resumeService.getAllResumeOfUser(login);
        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }

    @PostMapping(value = "/{email}/resume")
    public ResponseEntity<?> createResume(@PathVariable("email") @NotBlank @Valid String email,
                                          @RequestBody Resume resume) {
        Resume createdResume = resumeService.add(resume);
        return new ResponseEntity<>(createdResume, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{email}/{resumeName}")
    public ResponseEntity<?> removeResume(@PathVariable("email") @NotBlank @Valid String email, @PathVariable("resumeName") @NotBlank @Valid String resumeName) {
        resumeService.remove(resumeName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{email}/{resumeName}")
    public ResponseEntity<?> updateResume(@PathVariable("email") @NotBlank @Valid String email,
                                          @PathVariable("resumeName") @NotBlank @Valid String resumeName,
                                          @RequestBody Resume resume) {
        resumeService.update(resumeName, resume);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
