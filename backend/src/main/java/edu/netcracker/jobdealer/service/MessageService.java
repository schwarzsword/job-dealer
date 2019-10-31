package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.MessageNotFoundException;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message sendMessage(String text, Account src, Account dest);

    List<Message> getUserMessages(Account user);

    void deleteMessage(Message message);

    Message getMessage(UUID mesId) throws MessageNotFoundException;
}
