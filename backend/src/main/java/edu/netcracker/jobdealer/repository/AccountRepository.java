package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String login);

    boolean existsByEmail(String email);
}