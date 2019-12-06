package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {

    Set<Resume> findAllBySalaryIsGreaterThanEqual(int money);

    List<Resume> findAllBySalary(int money);

    Set<Resume> findAllBySalaryIsLessThanEqual(int money);

    Optional<Resume> findByNameAndApplicantAccountEmail(String resumeName, String email);

    void deleteByNameAndApplicantAccountEmail(String resumeName, String email);

    List<Resume> findAllByApplicant(Applicant applicant);

    List<Resume> findAllByApplicantAccount(Account account);

    List<Resume> findAllByApplicantAccountEmail(String email);

    void deleteAllByApplicant(Applicant applicant);

    void deleteAllByApplicantAccount(Account account);

    void deleteAllByApplicantAccountEmail(String email);

    boolean existsByNameAndApplicantId(String name, UUID applicantId);

    boolean existsByNameAndApplicant(String name, Applicant applicant);

    Set<Resume> findDistinctBySkillsContains(Skills skills);

}
