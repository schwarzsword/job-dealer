package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.ResumeDto;

import java.util.List;
import java.util.UUID;

public interface ResumeService {

    List<ResumeDto> getAllResumes();
    ResumeDto getResumeById(UUID id);
}
