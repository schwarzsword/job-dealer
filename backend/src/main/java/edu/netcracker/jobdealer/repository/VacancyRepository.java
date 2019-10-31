package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VacancyRepository extends JpaRepository<Vacancy, UUID> {
    Optional<Vacancy> findById(UUID vacancyId);

    List<Vacancy> findAllByMoneyIsGreaterThanEqual(int money);

    List<Vacancy> findAllByMoneyIsLessThanEqual(int money);

    List<Vacancy> findAllByOwner(Company company);

    List<Vacancy> findAllByRequestedSkillsContains(Skills skill);

    Vacancy findByNameAndOwner(String name, Company owner);

    List<Vacancy> findByOwner(Company company);
}