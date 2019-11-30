package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ApplicantServiceImpl(ApplicantRepository applicantRepository, AccountRepository accountRepository) {
        this.applicantRepository = applicantRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public Applicant getApplicantById(UUID id) {
        return applicantRepository.findById(id).orElseThrow(ApplicantNotFoundException::new);
    }

    @Override
    public Applicant addApplicant(UUID accountId) throws AccountAlreadyInUseException, AccountByIdNotFoundException {
        if (!applicantRepository.existsByAccount_Id(accountId)) {
            Account account = accountRepository.findById(accountId).orElseThrow(ApplicantNotFoundException::new);
            return applicantRepository.save(new Applicant(account));
        } else throw new AccountAlreadyInUseException();
    }

    @Override
    public Applicant updateApplicant(String firstName, String lastName, String middleName, UUID accountId) {
        throw new NotImplementedMethodException("Method is not implemented");
    }

    @Override
    public ResponseEntity deleteApplicant(UUID id) throws ApplicantNotFoundException {
        throw new NotImplementedMethodException("Method is not implemented");
    }

    @Override
    public Applicant getByAccount(Account accountByEmail) throws ApplicantNotFoundException {
        return applicantRepository.findByAccount(accountByEmail).orElseThrow(CompanyNotFoundException::new);
    }
}
