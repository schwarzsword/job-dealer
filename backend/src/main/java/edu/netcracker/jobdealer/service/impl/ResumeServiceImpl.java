package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.ResumeRepository;
import edu.netcracker.jobdealer.repository.SkillsRepository;
import edu.netcracker.jobdealer.service.JsonService;
import edu.netcracker.jobdealer.service.ResumeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static edu.netcracker.jobdealer.util.FileWorker.extractBytes;


@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final SkillsRepository skillsRepository;
    private final ApplicantRepository applicantRepository;
    private final JsonService jsonService;

    @Value("${upload.path}")
    private String path;

    public ResumeServiceImpl(ResumeRepository resumeRepository,
                             SkillsRepository skillsRepository,
                             ApplicantRepository applicantRepository, JsonService jsonService) {
        this.resumeRepository = resumeRepository;
        this.skillsRepository = skillsRepository;
        this.applicantRepository = applicantRepository;
        this.jsonService = jsonService;
    }

    @Override
    public Resume add(String resumeDataString) {
        ResumeDto resumeData = jsonService.parseResumeDto(resumeDataString);
        Applicant applicant = applicantRepository.findById(resumeData.getApplicantId())
                .orElseThrow(ApplicantNotFoundException::new);
        List<Skills> skills = resumeData.getSkills().stream()
                .map(e -> skillsRepository.findByName(e)
                        .orElseThrow(SkillNotFoundException::new))
                .collect(Collectors.toList());
        try {
            if (resumeData.getFileData() == null) {
                resumeData.setFileData(extractBytes(path));
            }
        } catch (IOException e){
            throw new BadException();
        }
        if (!resumeRepository.existsByNameAndApplicant(resumeData.getName(), applicant)) {
            Resume resume = resumeRepository.save(
                    new Resume(resumeData.getName(), resumeData.getFirstName(),
                            resumeData.getLastName(), resumeData.getSalary(),
                            resumeData.getFileData(), resumeData.getAbout(),
                            applicant, skills));
            List<Resume> ownedResumes = applicant.getOwnedResumes();
            ownedResumes.add(resume);
            applicant.setOwnedResumes(ownedResumes);
//            applicant.setActiveResume(resume);
            applicantRepository.save(applicant);
            return resume;
        } else throw new ResumeAlreadyExistsException();
    }

    //todo avatarUrl -> fileData[]
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
//            if (!resumeToUpdate.getAvatarUrl().equals(avatarUrl)) {
//                resumeToUpdate.setAvatarUrl(avatarUrl);
//            }
            if (!(resumeToUpdate.getSalary() == salary)) {
                resumeToUpdate.setSalary(salary);
            }


            Set<Skills> updatedSkillString = new HashSet<>(resumeToUpdate.getSkills());
            updatedSkillString.addAll(new HashSet<>(skillsString));
            resumeToUpdate.setSkills(new ArrayList<>(updatedSkillString));
            return resumeToUpdate;
        } else {
            throw new ResumeNotFoundException();
        }
    }


//    @Override
//    public void remove(UUID resumeId) {
//        Resume resumeToDelete = resumeRepository.findById(resumeId).orElseThrow(
//                () -> {
//                    throw new ResourceNotFoundException();
//                }
//        );
//        resumeRepository.delete(resumeToDelete);
//    }

    @Override
    public List<Resume> getAllResumeOfUser(String login) {
        throw new NotImplementedMethodException("Method is not implemented");
    }

}

