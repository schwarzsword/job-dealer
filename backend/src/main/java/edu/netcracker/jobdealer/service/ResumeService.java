package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import edu.netcracker.jobdealer.exceptions.ResumeAlreadyExistsException;

import java.util.List;
import java.util.UUID;

public interface ResumeService {

    public Resume add(String resumeName, String firstName,
                      String lastName, String about,
                      String avataUrl, int salary,
                      UUID applicantId, List<String> skillsString)
            throws ApplicantNotFoundException, ResumeAlreadyExistsException;

    Resume update(String resumeName, Resume resume, String email);
  
    void remove(UUID resumeId);

    List<Resume> getAllResumeOfUser(String login);
}

