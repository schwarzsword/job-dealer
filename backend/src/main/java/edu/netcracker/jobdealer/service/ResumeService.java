package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.ResumeFilters;
import edu.netcracker.jobdealer.entity.Resume;

import java.util.List;
import java.util.UUID;

public interface ResumeService {

    Resume addOrUpdate(String resumeData);

    Resume getResume(UUID id);

    List<Resume> getOwnedResumes(String email);

    void deleteResume(UUID id, String email);

    List<Resume> getPage(List<Resume> inp, int offset, int limit);

    List<Resume> applyConditions(ResumeFilters vacancyFilters);

    int getSize(String filters);

    List<Resume> sortAndReturn(String filters);
}

