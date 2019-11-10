package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.repository.ResumeRepository;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.ApplicantService;
import edu.netcracker.jobdealer.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final AccountService accountService;
    private final ApplicantService applicantService;

    @Autowired
    public ResumeServiceImpl(final ResumeRepository resumeRepository,
                             final AccountService accountService,
                             final ApplicantService applicantService) {
        this.resumeRepository = resumeRepository;
        this.accountService = accountService;
        this.applicantService = applicantService;
    }

    @Override
    public List<Resume> getAll() {
        return resumeRepository.findAll();
    }

    @Override
    public Resume add(Resume resume) {
        return resumeRepository.saveAndFlush(resume);
    }

    @Override
    public Resume update(String resumeName, Resume resume, String ownerEmail) {
        Resume resumeToUpdate = resumeRepository.findByResumeNameAndOwner_Account_Email(resumeName, ownerEmail);

        if (resume.getAbout() != null) {
            resumeToUpdate.setAbout(resume.getAbout());
        }
        if (resume.getFirstName() != null) {
            resumeToUpdate.setFirstName(resume.getFirstName());
        }
        if (resume.getLastName() != null) {
            resumeToUpdate.setLastName(resume.getLastName());
        }
        if (resume.getMiddleName() != null) {
            resumeToUpdate.setMiddleName(resume.getMiddleName());
        }
        if (resume.getAvatarUrl() != null) {
            resumeToUpdate.setAvatarUrl(resume.getAvatarUrl());
        }
        if (resume.getSalary() != null) {
            resumeToUpdate.setSalary(resume.getSalary());
        }
        if (resume.getSkills() != null) {
            resume.setSkills(resume.getSkills());
        }

        return resumeRepository.saveAndFlush(resumeToUpdate);
    }

    @Override
    public void remove(String resumeName, String ownerEmail) {
        Resume resumeToDelete = resumeRepository.findByResumeNameAndOwner_Account_Email(resumeName, ownerEmail);
        resumeRepository.delete(resumeToDelete);
    }

    @Override
    public List<Resume> getAllResumeOfUser(String login) {
        return resumeRepository.findAllByOwner_Account_Email(login);
    }


}
