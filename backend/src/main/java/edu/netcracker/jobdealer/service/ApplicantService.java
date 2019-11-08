package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Applicant;

import java.util.List;
import java.util.UUID;

public interface ApplicantService {

    List<Applicant> getAllApplicants();
    Applicant getApplicantById(UUID id);
}
