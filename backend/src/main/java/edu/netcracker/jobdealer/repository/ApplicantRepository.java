package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
    Optional<Applicant> findByAccount(Account account);

    Optional<Applicant> findById(UUID id);


}