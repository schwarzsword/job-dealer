package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import edu.netcracker.jobdealer.exceptions.ResumeAlreadyExistsException;
import edu.netcracker.jobdealer.service.ResumeService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class ResumeController {

    private final ResumeService resumeService;
    private final Mapper mapper;
    @Value("${upload.path}")
    private String path;

    public ResumeController(ResumeService resumeService, Mapper mapper) {
        this.resumeService = resumeService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/resumes")
    public ResponseEntity<?> createResume(@RequestParam String resumeName, @RequestParam String firstName,
                                          @RequestParam String lastName, @RequestParam String about,
                                          @RequestParam("file") MultipartFile file, @RequestParam UUID applicantId,
                                          @RequestParam List<String> skills, @RequestParam long salary) {


        //todo добавить проверку, если файл null то дальше прокидывать дефолтный урл картинки, которую мы положим заранее

        //todo решить, где хранить файлы

        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String avatarUrl = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        try {
            file.transferTo(new File(path + "/" + avatarUrl));
            Resume add = resumeService.add(resumeName, firstName, lastName, about, avatarUrl, salary, applicantId, skills);
            return ResponseEntity.ok(mapper.map(add, ResumeDto.class));
        } catch (IOException | ResumeAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ApplicantNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
