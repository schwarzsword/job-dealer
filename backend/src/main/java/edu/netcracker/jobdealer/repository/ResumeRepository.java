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

    List<Resume> findAllBySalaryIsGreaterThanEqual(int money);

    List<Resume> findAllBySalary(int money);

    List<Resume> findAllBySalaryIsLessThanEqual(int money);

    Resume findByNameAndApplicant_Account_Email(String resumeName, String email);

    void deleteByNameAndApplicant_Account_Email(String resumeName, String email);

    List<Resume> findAllByApplicant(Applicant applicant);

    List<Resume> findAllByApplicant_Account(Account account);

    List<Resume> findAllByApplicant_Account_Email(String email);

    void deleteAllByApplicant(Applicant applicant);

    void deleteAllByApplicant_Account(Account account);

    void deleteAllByApplicant_Account_Email(String email);

    boolean existsByNameAndApplicant_Id(String name, UUID applicantId);

    boolean existsByNameAndApplicant(String name, Applicant applicant);
}
