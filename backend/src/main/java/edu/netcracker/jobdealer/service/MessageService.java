package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Message;

import java.util.List;
import java.util.UUID;

/**
 * Message service interface for managing messages.
 */
public interface MessageService {

    /**
     * Gets a list of messages by the identifier of the interlocutor
     * and can receive by limit and offset
     *
     * @param senderId   sender identifier
     * @param receiverId receiver identifier
     * @param offset     offset for message list
     * @param limit      limit for message list
     * @return List of messages
     * @see UUID
     * @see Message
     */
    List<Message> getMessages(UUID senderId, UUID receiverId, Integer offset, Integer limit);

    /**
     * Gets a message by identifier
     *
     * @param messageId Message identifier
     * @return Message object
     * @see Message
     */
    Message getMessage(UUID messageId);

    /**
     * Sends a message from sender to receiver by identifier
     *
     * @param text       Message body
     * @param senderId   Sender uuid
     * @param receiverId Receiver uuid
     * @return Message object
     * @see UUID
     */
    Message sendMessage(String text, UUID senderId, UUID receiverId);

    /**
     * Sends a message from sender to receiver by Account
     *
     * @param message Message object
     * @return Message object
     * @see Message
     */
    Message sendMessage(Message message);

    /**
     * Updates the message by identifier
     *
     * @param id   Message uuid
     * @param text Message body
     * @return Message object
     * @see UUID
     * @see Message
     */
    Message updateMessage(UUID id, String text);

    /**
     * Deletes the message by identifier
     *
     * @param id Message uuid
     */
    void deleteMessage(UUID id);
}
