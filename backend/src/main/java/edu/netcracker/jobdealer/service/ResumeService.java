package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Resume;

import java.util.List;

public interface ResumeService {

    Resume add(Resume resume);
    Resume update(String resumeName, Resume resume, String email);
    void remove(String resumeName, String email);
    List<Resume> getAllResumeOfUser(String login);
}
