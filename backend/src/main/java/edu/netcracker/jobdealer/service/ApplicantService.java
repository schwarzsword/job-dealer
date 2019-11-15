package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ApplicantService {

    List<Applicant> getAllApplicants();
    Applicant getApplicantById(UUID id);
    Applicant addApplicant(String firstName, String lastName, String middleName, UUID accountId);
    Applicant updateApplicant(String firstName, String lastName, String middleName, UUID accountId);
    ResponseEntity deleteApplicant(UUID id) throws ApplicantNotFoundException;
}
