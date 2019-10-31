package edu.netcracker.jobdealer.service;


import edu.netcracker.jobdealer.entity.Resume;

import java.util.List;

public interface ResumeService {
    List<Resume> getAll();

    Resume add(Resume resume);

    Resume update(String resumeName, Resume resume);

    void remove(String resumeName);

    List<Resume> getAllResumeOfUser(String login);

}
