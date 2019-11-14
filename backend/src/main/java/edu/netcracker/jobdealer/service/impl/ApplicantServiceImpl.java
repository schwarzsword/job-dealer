package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    @Autowired
    public ApplicantServiceImpl(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public Applicant getApplicantById(UUID id) {
        Optional<Applicant> applicant = applicantRepository.findById(id);

        if (applicant.isPresent()) {
            return applicant.get();
        } else {
            throw new ApplicantNotFoundException("Applicant is not found.");
        }
    }

    @Override
    public Applicant addApplicant(String firstName, String lastName, String middleName, UUID accountId) {
        return null;
    }

    @Override
    public Applicant updateApplicant(String firstName, String lastName, String middleName, UUID accountId) {
        return null;
    }

    @Override
    public ResponseEntity deleteApplicant(UUID id) throws ApplicantNotFoundException {
        return null;
    }
}
