package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {

    List<Applicant> findAll();
    Applicant getById(UUID id);
}
