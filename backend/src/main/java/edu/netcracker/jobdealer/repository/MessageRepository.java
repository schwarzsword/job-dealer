package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findAllByMessageDest_Email(String email);

    List<Message> findAllByMessageSource_Email(String email);

    List<Message> findAllByMessageDest(Account account);

    List<Message> findAllByMessageSource(Account account);

    void deleteAllByMessageDest(Account account);

    void deleteAllByMessageSource(Account account);

    void deleteAllByMessageDest_Email(String email);

    void deleteAllByMessageSource_Email(String email);
}