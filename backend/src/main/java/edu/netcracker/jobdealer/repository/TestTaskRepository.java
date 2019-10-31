package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestTaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByName(String keyword);

    Optional<Task> findByVacancy(Vacancy vacancy);
}