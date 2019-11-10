package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.MessageNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoRightsException;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message sendMessage(String text, String srcEmail, String destEmail) throws AccountNotFoundException;

    List<Message> getUserMessages(UUID userId);

    List<Message> getUserMessages(String email);

    void deleteMessage(UUID mesId, String ownerEmail) throws MessageNotFoundException, NoRightsException;

    Message getMessage(UUID mesId) throws MessageNotFoundException;
}
