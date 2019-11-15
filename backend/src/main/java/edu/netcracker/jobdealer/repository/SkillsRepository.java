package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SkillsRepository extends JpaRepository<Skills, UUID> {
    Optional<Skills> findByName(String skill);

    List<Skills> findAllByNameContaining(String st);

    void deleteAllByNameContaining(String st);

    boolean existsByName(String name);
}