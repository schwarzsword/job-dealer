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
     * @see UUID
     * @see Message
     *
     * @param senderId sender identifier
     * @param receiverId receiver identifier
     * @param offset offset for message list
     * @param limit limit for message list
     * @return List of messages
     */
    List<Message> getMessages(UUID senderId, UUID receiverId, Integer offset, Integer limit);

    /**
     * Gets a message by identifier
     *
     * @see Message
     *
     * @param messageId Message identifier
     * @return Message object
     */
    Message getMessage(UUID messageId);

    /**
     * Sends a message from sender to receiver by identifier
     *
     * @see UUID
     *
     * @param text Message body
     * @param senderId Sender uuid
     * @param receiverId Receiver uuid
     * @return Message object
     */
    Message sendMessage(String text, UUID senderId, UUID receiverId);

    /**
     * Sends a message from sender to receiver by Account
     *
     * @see Message
     *
     * @param message Message object
     * @return Message object
     */
    Message sendMessage(Message message);

    /**
     * Updates the message by identifier
     *
     * @see UUID
     * @see Message
     *
     * @param id Message uuid
     * @param text Message body
     * @return Message object
     */
    Message updateMessage(UUID id, String text);

    /**
     * Deletes the message by identifier
     * @param id Message uuid
     */
    void deleteMessage(UUID id);
}
