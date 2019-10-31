package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;

public interface ApplicantService {
    Applicant getByAccount(Account account);
}
