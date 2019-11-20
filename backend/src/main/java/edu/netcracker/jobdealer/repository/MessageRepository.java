package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findAllByMessageDestEmail(String email);

    List<Message> findAllByMessageDestId(UUID id);

    List<Message> findAllByMessageSourceEmail(String email);

    List<Message> findAllByMessageSourceId(UUID id);

    List<Message> findAllByMessageDest(Account account);

    List<Message> findAllByMessageSource(Account account);

    void deleteAllByMessageDest(Account account);

    void deleteAllByMessageSource(Account account);

    void deleteAllByMessageDestEmail(String email);

    void deleteAllByMessageDestId(UUID id);

    void deleteAllByMessageSourceEmail(String email);

    void deleteAllByMessageSourceId(UUID id);

    Optional<Message> findByMessageDestEmailAndMessageSourceEmail(String dest, String source);

    Optional<Message> findByMessageDestIdAndMessageSourceId(UUID dest, UUID source);
}