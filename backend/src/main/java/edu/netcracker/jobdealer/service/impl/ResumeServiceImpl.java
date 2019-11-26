package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.ResumeRepository;
import edu.netcracker.jobdealer.repository.SkillsRepository;
import edu.netcracker.jobdealer.service.ResumeService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
                      String avataUrl, int salary,
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
    public Resume update(UUID resumeId,
                         String resumeName,
                         String firstName,
                         String lastName,
                         String about,
                         String avatarUrl,
                         int salary,
                         List<Skills> skillsString) {
        Optional<Resume> optionalResumeToUpdate = resumeRepository.findById(resumeId);

        if (optionalResumeToUpdate.isPresent()) {
            Resume resumeToUpdate = optionalResumeToUpdate.get();
            if (!resumeToUpdate.getName().equals(resumeName)) {
                resumeToUpdate.setName(resumeName);
            }
            if (!resumeToUpdate.getFirstName().equals(firstName)) {
                resumeToUpdate.setFirstName(firstName);
            }
            if (!resumeToUpdate.getLastName().equals(lastName)) {
                resumeToUpdate.setLastName(lastName);
            }
            if (!resumeToUpdate.getAbout().equals(about)) {
                resumeToUpdate.setAbout(about);
            }
            if (!resumeToUpdate.getAvatarUrl().equals(avatarUrl)) {
                resumeToUpdate.setAvatarUrl(avatarUrl);
            }
            if (!(resumeToUpdate.getSalary() == salary)) {
                resumeToUpdate.setSalary(salary);
            }


            Set<Skills> updatedSkillString = resumeToUpdate.getSkills().stream().collect(Collectors.toSet());
            updatedSkillString.addAll(skillsString.stream().collect(Collectors.toSet()));
            resumeToUpdate.setSkills(updatedSkillString.stream().collect(Collectors.toList()));
            return resumeToUpdate;
        } else {
            throw new ResumeNotFoundException();
        }
    }


    @Override
    public void remove(UUID resumeId) {
        Resume resumeToDelete = resumeRepository.findById(resumeId).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException();
                }
        );
        resumeRepository.delete(resumeToDelete);
    }

    @Override
    public List<Resume> getAllResumeOfUser(String login) {
        throw new NotImplementedMethodException("Method is not implemented");
    }


}

