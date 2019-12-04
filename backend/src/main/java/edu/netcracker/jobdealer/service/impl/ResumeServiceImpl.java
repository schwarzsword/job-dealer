package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.entity.*;
import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.ResumeRepository;
import edu.netcracker.jobdealer.repository.SkillsRepository;
import edu.netcracker.jobdealer.service.JsonService;
import edu.netcracker.jobdealer.service.ResumeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<Resume> getResumes(int page, int limit, String sortBy) {
        Pageable paging;
        if (sortBy == null) {
            paging = PageRequest.of(page, limit);
        } else {
            paging = PageRequest.of(page, limit, Sort.by(sortBy));
        }
        Page<Resume> pagedResult = resumeRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            throw new ResumeNotFoundException();
        }
    }


    @Override
    public List<Resume> getAllResumeOfUser(String login) {
        throw new NotImplementedMethodException("Method is not implemented");
    }

    @Override
    public List<Resume> getPage(List<Resume> resumes, int offset, int limit) {
        int size = resumes.size();
        if (size > offset) {
            return resumes.subList(offset, limit > (size - offset) ? size : (limit + offset));
        } else return null;
    }

    //todo : переделать актуально
     @Override
     public List<Resume> sortAndReturn(String country,
                                       String city,
                                       int salaryMin,
                                       int salaryMax,
                                       boolean experience,
                                       boolean driverLicence,
                                       int offset,
                                       int limit,
                                       String sortBy) {
         List<Resume> vacancies = applyConditions(country, city, salaryMax, experience, driverLicence);
         switch (sortBy) {
             case "Vacancy name descending":
                 vacancies.sort(Comparator.comparing(Resume::getName).reversed());
                 break;
             case "Salary ascending":
                 vacancies.sort(Comparator.comparing(Resume::getSalary));
                 break;
             case "Salary descending":
                 vacancies.sort(Comparator.comparing(Resume::getSalary).reversed());
                 break;
             case "City name ascending":
                 vacancies.sort(Comparator.comparing(Resume::getCity));
                 break;
             case "City name descending":
                 vacancies.sort(Comparator.comparing(Resume::getCity).reversed());
                 break;
             case "Country name ascending":
                 vacancies.sort(Comparator.comparing(Resume::getCountry));
                 break;
             case "Country name descending":
                 vacancies.sort(Comparator.comparing(Resume::getCountry).reversed());
                 break;
             case "Experience":
                 vacancies.sort(Comparator.comparing(Resume::getExperience));
                 break;
             case "DriverLicence":
                 vacancies.sort(Comparator.comparing(Resume::getDriverLicence));
                 break;
             default:
                 vacancies.sort(Comparator.comparing(Resume::getName));
         }
         return getPage(vacancies, offset, limit);
     }

    @Override
    public List<Resume> applyConditions(String country,
                                         String city,
                                         Integer salary,
                                         Boolean experience,
                                         Boolean driverLicence) {
        if (country == null && salary == null && city == null && experience == null && driverLicence == null)
            throw new BadParameterException("Can't search with empty parameters");
        Set<Resume> resumes = new HashSet<Resume>(resumeRepository.findAll());
        if (salary != null) {
            Set<Resume> allByMoneyIsGreaterThanEqual = resumeRepository.findAllBySalaryIsGreaterThanEqual(salary);
            resumes.retainAll(allByMoneyIsGreaterThanEqual);
        }

        if (country != null) {
            Set<Resume> allByNameLike = resumeRepository.findByCountry(country);
            resumes.retainAll(allByNameLike);
        }

        if (city != null) {
            Set<Resume> allByNameLike = resumeRepository.findByCity(city);
            resumes.retainAll(allByNameLike);
        }

        if (experience != null) {
            Set<Resume> allByNameLike = resumeRepository.findByExperience(experience);
            resumes.retainAll(allByNameLike);
        }

        if (driverLicence != null) {
            Set<Resume> allByNameLike = resumeRepository.findByDriverLicence(driverLicence);
            resumes.retainAll(allByNameLike);
        }

        return new ArrayList<>(resumes);
    }
}

