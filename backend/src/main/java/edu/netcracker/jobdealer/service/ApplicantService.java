package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.ApplicantDto;

import java.util.List;
import java.util.UUID;

public interface ApplicantService {

    List<ApplicantDto> getAllApplicants();
    ApplicantDto getApplicantById(UUID id);
}
