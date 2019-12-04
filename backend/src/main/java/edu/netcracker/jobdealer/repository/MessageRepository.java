package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    /**
     * Gets a list of messages by sender identifier and receiver identifier.
     *
     * @param senderId   sender identifier
     * @param receiverId receiver identifier
     * @return List of message objects
     */
    List<Message> findAllBySenderIdAndReceiverIdOrderByDate(UUID senderId, UUID receiverId);
}
