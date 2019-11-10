package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.MessageNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoRightsException;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.MessageRepository;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Transactional
@Service
public class MessageServiceImpl implements MessageService {


    private final AccountService accountService;

    private final MessageRepository messageRepository;

    private final AccountRepository accountRepository;

    public MessageServiceImpl(MessageRepository messageRepository, AccountRepository accountRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }


    @Override
    public Message sendMessage(String text, String srcEmail, String destEmail) throws AccountNotFoundException {
        Account src = accountService.getByEmail(srcEmail);
        Account dest = accountService.getByEmail(destEmail);
        Message message = new Message(text, src, dest);
        messageRepository.save(message);
        return message;
    }

    @Override
    public List<Message> getUserMessages(UUID userId) {
        return messageRepository.findAllByMessageDest_Id(userId);
    }

    @Override
    public List<Message> getUserMessages(String email) {
        return messageRepository.findAllByMessageDest_Email(email);
    }


    @Override
    public void deleteMessage(UUID mesId, String ownerEmail) throws MessageNotFoundException, NoRightsException {
        Message message = getMessage(mesId);
        if (message.getMessageDest().getEmail().equals(ownerEmail)) {
            messageRepository.delete(message);
        } else throw new NoRightsException("You can delete only owned messages");
    }

    @Override
    public Message getMessage(UUID mesId) throws MessageNotFoundException {
        Optional<Message> byId = messageRepository.findById(mesId);
        if (byId.isPresent()) {
            return byId.get();
        } else throw new MessageNotFoundException("Message not found");
    }
}
