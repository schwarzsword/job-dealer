package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {

    List<Resume> findAllBySalaryIsGreaterThanEqual(long money);

    List<Resume> findAllBySalary(long money);

    List<Resume> findAllBySalaryIsLessThanEqual(long money);

    Resume findByNameAndApplicantAccountEmail(String resumeName, String email);

    void deleteByNameAndApplicantAccountEmail(String resumeName, String email);

    List<Resume> findAllByApplicant(Applicant applicant);

    List<Resume> findAllByApplicantAccount(Account account);

    List<Resume> findAllByApplicantAccountEmail(String email);

    void deleteAllByApplicant(Applicant applicant);

    void deleteAllByApplicantAccount(Account account);

    void deleteAllByApplicantAccountEmail(String email);

    boolean existsByNameAndApplicantId(String name, UUID applicantId);

    boolean existsByNameAndApplicant(String name, Applicant applicant);
}
