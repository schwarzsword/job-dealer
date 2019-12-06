package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.dto.ResumeFilters;
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
    public Resume addOrUpdate(String resumeDataString) {
        ResumeDto resumeData = jsonService.parseResumeDto(resumeDataString);

        List<Skills> skills = resumeData.getSkills().stream()
                .map(e -> skillsRepository.findByName(e)
                        .orElseThrow(SkillNotFoundException::new))
                .collect(Collectors.toList());
        try {
            if (resumeData.getFileData() == null) {
                resumeData.setFileData(extractBytes(path));
            }
        } catch (IOException e) {
            throw new BadException();
        }
        Applicant applicant = applicantRepository.findById(resumeData.getApplicantId())
                .orElseThrow(ApplicantNotFoundException::new);
        Resume resume = new Resume(resumeData.getName(), resumeData.getFirstName(),
                resumeData.getLastName(), resumeData.getSalary(),
                resumeData.getFileData(), resumeData.getAbout(),
                applicant, skills);
        if (resumeData.getId() == null) {
            resumeRepository.save(resume);
            List<Resume> ownedResumes = applicant.getOwnedResumes();
            ownedResumes.add(resume);
            applicant.setOwnedResumes(ownedResumes);
            applicant.setActiveResume(resume);
            applicantRepository.save(applicant);
            return resume;
        } else {
            resume.setId(resumeData.getId());
            return resumeRepository.save(resume);
        }
    }

    @Override
    public Resume getResume(UUID id) {
        return resumeRepository.findById(id).orElseThrow(ResumeNotFoundException::new);
    }

    @Override
    public List<Resume> getOwnedResumes(String email) {
        Applicant applicant = applicantRepository.findByAccountEmail(email).orElseThrow(ApplicantNotFoundException::new);
        return applicant.getOwnedResumes();
    }

    @Override
    public void deleteResume(UUID id, String email) {
        Resume resume = resumeRepository.findById(id).orElseThrow(VacancyNotFoundException::new);
        if (email.equals(resume.getApplicant().getAccount().getEmail())) {
            resumeRepository.delete(resume);
        } else throw new NoPermissionException();
    }

    @Override
    public List<Resume> getPage(List<Resume> resumes, int offset, int limit) {
        int size = resumes.size();
        if (size > offset) {
            return resumes.subList(offset, limit > (size - offset) ? size : (limit + offset));
        } else return Collections.emptyList();
    }

    @Override
    public List<Resume> applyConditions(ResumeFilters resumeFilters) {
        if (resumeFilters.getMaxSalary() < 0
                && resumeFilters.getMinSalary() < 0
                && resumeFilters.getSkills().size() == 0
        )
            throw new BadParameterException("Can't search with empty parameters");
        Set<Resume> resumes = new HashSet<>(resumeRepository.findAll());
        Set<Resume> allBySalaryIsGreaterThanEqual = resumeRepository
                .findAllBySalaryIsGreaterThanEqual(resumeFilters.getMinSalary());
        resumes.retainAll(allBySalaryIsGreaterThanEqual);
        Set<Resume> allBySalaryIsLessThanEqual = resumeRepository
                .findAllBySalaryIsLessThanEqual(resumeFilters.getMaxSalary());
        resumes.retainAll(allBySalaryIsLessThanEqual);
        if (resumeFilters.getSkills().size() != 0) {
            List<Skills> Skills = resumeFilters.getSkills().stream()
                    .map(e -> skillsRepository.findByName(e)
                            .orElseThrow(SkillNotFoundException::new))
                    .collect(Collectors.toList());
            Skills.forEach(e -> {
                Set<Resume> allByRequestedSkillsContains = resumeRepository.findDistinctBySkillsContains(e);
                resumes.retainAll(allByRequestedSkillsContains);
            });
        }
        return new ArrayList<>(resumes);
    }

    @Override
    public int getSize(String filters) {
        ResumeFilters resumeFilters = jsonService.parseResumeFilters(filters);
        return applyConditions(resumeFilters).size();
    }

    @Override
    public List<Resume> sortAndReturn(String filters) {
        ResumeFilters resumeFilters = jsonService.parseResumeFilters(filters);
        List<Resume> resumes = applyConditions(resumeFilters);
        switch (resumeFilters.getSortBy()) {
            case "Salary":
                resumes.sort(Comparator.comparing(Resume::getSalary));
                break;
            default:
                resumes.sort(Comparator.comparing(Resume::getName));
        }
        if (resumeFilters.isDescending()) {
            Collections.reverse(resumes);
        }
        return getPage(resumes, resumeFilters.getOffset(), resumeFilters.getLimit());
    }


}

