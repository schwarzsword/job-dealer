package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ApplicantService {

    List<Applicant> getAllApplicants();

    Applicant getApplicantById(UUID id);

    Applicant addApplicant(UUID accountId);

    Applicant updateApplicant(String firstName, String lastName, String middleName, UUID accountId);

    ResponseEntity deleteApplicant(UUID id);

    Applicant getByAccount(Account accountByEmail);

    Applicant who(String email);
}
