package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByAccount_Email(String email);

    Optional<Company> findById(UUID id);

    void deleteByAccount_Email(String email);

    Company getById(UUID id);

    Optional<Company> findByAccount(Account account);

    boolean existsByAccount_Id(UUID id);
}
