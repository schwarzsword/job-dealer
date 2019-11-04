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

    List<Vacancy> findAllByOwner_Account_Email(String email);

    List<Vacancy> findAllByRequestedSkillsContains(Skills skill);

    Vacancy findByNameAndOwner(String name, Company owner);

    List<Vacancy> findAllByNameContainingAndOwner(String name, Company owner);

    List<Vacancy> findAllByNameContaining(String name);

    void deleteByNameAndOwner(String name, Company owner);

    Vacancy findByNameAndOwner_Account_Email(String name, String email);

    List<Vacancy> findAllByNameContainingAndOwner_Account_Email(String name, String email);

    void deleteByNameAndOwner_Account_Email(String name, String email);
}