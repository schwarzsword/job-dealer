package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByAccountEmail(String email);

    void deleteByAccountEmail(String email);

    Optional<Company> findByAccount(Account account);

    boolean existsByAccount_Id(UUID id);

    Optional<Company> findFirstByName(String name);

    boolean existsByName(String string);
}
