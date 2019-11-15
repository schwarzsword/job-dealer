package edu.netcracker.jobdealer.service;


import edu.netcracker.jobdealer.entity.Resume;

import java.util.List;
import java.util.UUID;

public interface ResumeService {
    List<Resume> getAll();

    Resume add(Resume resume);

    Resume update(UUID resumeId, Resume resume);

    void remove(UUID resumeId);

    List<Resume> getAllResumeOfUser(UUID userId);

}
