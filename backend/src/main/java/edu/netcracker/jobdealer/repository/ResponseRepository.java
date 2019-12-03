package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResponseRepository extends JpaRepository<Response, UUID> {
    Optional<Response> findByVacancyId(UUID uuid);
}

