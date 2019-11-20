package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, UUID> {

    Optional<Vacancy> findById(UUID vacancyId);

    Set<Vacancy> findAllByMoneyIsGreaterThanEqual(int money);

    List<Vacancy> findAllByMoneyIsLessThanEqual(int money);

    List<Vacancy> findAllByOwner(Company company);

    List<Vacancy> findAllByOwner_Account_Email(String email);

    Set<Vacancy> findAllByRequestedSkillsContains(Skills skill);

    Vacancy findByNameAndOwner(String name, Company owner);

    List<Vacancy> findAllByNameContainingAndOwner(String name, Company owner);

    List<Vacancy> findAllByNameContaining(String name);

    void deleteByNameAndOwner(String name, Company owner);

    Vacancy findByNameAndOwnerAccountEmail(String name, String email);

    List<Vacancy> findAllByNameContainingAndOwnerAccountEmail(String name, String email);

    void deleteByNameAndOwnerAccountEmail(String name, String email);

}
