package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<Resume, UUID> {
    List<Resume> findAllBySalaryIsGreaterThanEqual(int money);

    List<Resume> findAllBySalary(int money);

    List<Resume> findAllBySalaryIsLessThanEqual(int money);

    Resume findByResumeName(String resumeName);

    @Override
    Optional<Resume> findById(UUID integer);

    List<Resume> findAllByOwner(Applicant applicant);

}