package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.exceptions.AccountAlreadyInUseException;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ApplicantService {

    List<Applicant> getAllApplicants();

    Applicant getApplicantById(UUID id);

    Applicant addApplicant(UUID accountId) throws AccountAlreadyInUseException, AccountByIdNotFoundException;

    Applicant updateApplicant(String firstName, String lastName, String middleName, UUID accountId);

    ResponseEntity deleteApplicant(UUID id) throws ApplicantNotFoundException;

    public Applicant getByAccount(Account accountByEmail) throws ApplicantNotFoundException;
}
