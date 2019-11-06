package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, UUID> {

    List<Vacancy> findAll();
    Vacancy getOne(UUID id);
}
