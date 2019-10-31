package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.MessageNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.MessageRepository;
import edu.netcracker.jobdealer.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final AccountRepository accountRepository;

    public MessageServiceImpl(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Message sendMessage(String text, Account src, Account dest) {
        Message message = new Message(text, src, dest);
        src.getMessagesAsSource().add(message);
        dest.getMessagesAsDest().add(message);
        messageRepository.save(message);
        accountRepository.save(src);
        accountRepository.save(dest);
        return message;
    }

    @Override
    public List<Message> getUserMessages(Account user) {
        return user.getMessagesAsDest();
    }


    @Override
    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public Message getMessage(Long mesId) throws MessageNotFoundException {
        Optional<Message> byId = messageRepository.findById(mesId);
        if (byId.isPresent()) {
            return byId.get();
        } else throw new MessageNotFoundException("Message not found");
    }
}
