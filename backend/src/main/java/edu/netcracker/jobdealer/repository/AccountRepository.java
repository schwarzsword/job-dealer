package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Account getById(UUID id);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String login);

    boolean existsByEmail(String email);
}
