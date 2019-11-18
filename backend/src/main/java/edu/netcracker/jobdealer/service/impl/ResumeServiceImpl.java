package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import edu.netcracker.jobdealer.exceptions.NotImplementedMethodException;
import edu.netcracker.jobdealer.exceptions.ResumeAlreadyExistsException;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.ResumeRepository;
import edu.netcracker.jobdealer.repository.SkillsRepository;
import edu.netcracker.jobdealer.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final SkillsRepository skillsRepository;
    private final ApplicantRepository applicantRepository;


    public ResumeServiceImpl(ResumeRepository resumeRepository,
                             SkillsRepository skillsRepository,
                             ApplicantRepository applicantRepository) {
        this.resumeRepository = resumeRepository;
        this.skillsRepository = skillsRepository;
        this.applicantRepository = applicantRepository;
    }


    @Override
    public Resume add(String resumeName, String firstName,
                      String lastName, String about,
                      String avataUrl, long salary,
                      UUID applicantId, List<String> skillsString)
            throws ApplicantNotFoundException, ResumeAlreadyExistsException {
        Applicant applicant = applicantRepository.findById(applicantId).orElseThrow(ApplicantNotFoundException::new);
        List<Skills> skills = skillsString.stream()
                .map(e -> skillsRepository.findByName(e)
                        .orElseGet(() -> skillsRepository
                                .save(new Skills(e))))
                .collect(Collectors.toList());
        if (!resumeRepository.existsByNameAndApplicant(resumeName, applicant)) {
            Resume resume = resumeRepository.save(
                    new Resume(resumeName, firstName, lastName, salary, avataUrl, about, applicant, skills));
            List<Resume> ownedResumes = applicant.getOwnedResumes();
            ownedResumes.add(resume);
            applicant.setOwnedResumes(ownedResumes);
            applicant.setActiveResume(resume);
            applicantRepository.save(applicant);
            return resume;
        } else throw new ResumeAlreadyExistsException();
    }

    @Override
    public Resume update(String resumeName, Resume resume, String email) {
        throw new NotImplementedMethodException("Method is not implemented");
    }

    @Override
    public void remove(String resumeName, String email) {
        throw new NotImplementedMethodException("Method is not implemented");
    }

    @Override
    public List<Resume> getAllResumeOfUser(String login) {
        throw new NotImplementedMethodException("Method is not implemented");
    }
}
