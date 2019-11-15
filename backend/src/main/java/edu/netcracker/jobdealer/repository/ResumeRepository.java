package edu.netcracker.jobdealer.repository;

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


    // TODO: Переписать методы
//    Resume findByNameAndOwner_Account_Email(String resumeName, String email);
//    void deleteByResumeNameAndOwner_Account_Email(String resumeName, String email);
//    List<Resume> findAllByOwner(Applicant applicant);
//    List<Resume> findAllByOwner_Account(Account account);
//    List<Resume> findAllByOwner_Account_Email(String email);
//    void deleteAllByOwner(Applicant applicant);
//    void deleteAllByOwner_Account(Account account);
//    void deleteAllByOwner_Account_Email(String email);
}
