package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {

    List<Resume> findAll();
    Resume getOne(UUID id);
}
