package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
    Optional<Skills> findByName(String skill);

}