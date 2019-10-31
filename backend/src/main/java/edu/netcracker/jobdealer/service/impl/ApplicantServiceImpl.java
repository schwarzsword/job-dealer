package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.exceptions.ResourceNotFoundException;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("applicantService")
@Transactional
public class ApplicantServiceImpl implements ApplicantService {
    private final ApplicantRepository applicantRepository;

    @Autowired
    public ApplicantServiceImpl(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public Applicant getByAccount(Account account) {
        return applicantRepository.findByAccount(account).orElseThrow(() ->
                new ResourceNotFoundException("Applicant for email "
                        + account.getEmail()
                        + " not found")
        );
    }
}
