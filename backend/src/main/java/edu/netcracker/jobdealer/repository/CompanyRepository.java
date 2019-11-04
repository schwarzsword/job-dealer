package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByAccount(Account account);

    Optional<Company> findByAccount_Email(String email);

    Optional<Company> findById(UUID id);

    void deleteByAccount_Email(String email);
}