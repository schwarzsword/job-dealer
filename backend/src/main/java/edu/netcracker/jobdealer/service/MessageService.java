package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.MessageNotFoundException;
import edu.netcracker.jobdealer.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message sendMessage(String text, Account src, Account dest);

    List<Message> getUserMessages(UUID userId) throws UserNotFoundException;

    void deleteMessage(Message message);

    Message getMessage(UUID mesId) throws MessageNotFoundException;
}
