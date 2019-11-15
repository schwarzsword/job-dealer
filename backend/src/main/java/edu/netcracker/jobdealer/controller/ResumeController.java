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

  
  //TODO пофиксить
    @RequestMapping(value = "/{login}/resumes/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllResumes(@PathVariable("login") @NotBlank @Valid String login) {

        List<Resume> resumes = resumeService.getAllResumeOfUser(login);
        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{email}/resume", method = RequestMethod.POST)
    public ResponseEntity<?> createResume(@PathVariable("email") @NotBlank @Valid String email,
                                          @RequestBody Resume resume) {
        Resume createdResume = resumeService.add(resume);
        return new ResponseEntity<>(createdResume, HttpStatus.OK);
    }

    @RequestMapping(value = "/{email}/{resumeName}", method = RequestMethod.DELETE)
    public ResponseEntity<?> getAllResumes(@PathVariable("email") @NotBlank @Valid String email,
                                           @PathVariable("resumeName") @NotBlank @Valid String resumeName) {
        resumeService.remove(resumeName, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{email}/{resumeName}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateResume(@PathVariable("email") @NotBlank @Valid String email,
                                          @PathVariable("resumeName") @NotBlank @Valid String resumeName,
                                          @RequestBody Resume resume) {
        resumeService.update(resumeName, resume, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
