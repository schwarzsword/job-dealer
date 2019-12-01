package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import edu.netcracker.jobdealer.exceptions.ResumeAlreadyExistsException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ResumeService {

    public Resume add(String resumeName, String firstName,
                      String lastName, String about,
                      byte[] fileData, int salary,
                      UUID applicantId, List<String> skillsString)
            throws ApplicantNotFoundException, ResumeAlreadyExistsException, IOException;

//todo переделать avatarUrl -> fileData[]
    Resume update(UUID resumeId,
                  String resumeName,
                  String firstName,
                  String lastName,
                  String about,
                  String avatarUrl,
                  int salary,
                  List<Skills> skillsString);
  
//    void remove(UUID resumeId);

    List<Resume> getAllResumeOfUser(String login);
    List<Resume> getResumes(int page, int limit, String sortBy);
}

