package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {

    Optional<Applicant> findByAccount(Account account);

    Optional<Applicant> findByAccount_Email(String email);

    Optional<Applicant> findById(UUID id);

    void deleteByAccount_Email(String email);

    List<Applicant> findAll();

    Applicant getById(UUID id);
}

