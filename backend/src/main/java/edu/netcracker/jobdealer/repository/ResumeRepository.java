package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    List<Resume> findAllByMoneyIsGreaterThanEqual(int money);

    List<Resume> findAllByMoney(int money);

    List<Resume> findAllByMoneyIsLessThanEqual(int money);

    Resume findByResumeName(String resumeName);

    @Override
    Optional<Resume> findById(Integer integer);

    List<Resume> findAllByOwner(Applicant applicant);

}