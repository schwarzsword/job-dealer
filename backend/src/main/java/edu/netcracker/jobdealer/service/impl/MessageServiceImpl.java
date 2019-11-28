package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;
import edu.netcracker.jobdealer.exceptions.MessageNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.MessageRepository;
import edu.netcracker.jobdealer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Message> getMessages(String username, UUID receiverId, Integer offset, Integer limit) {
        if (!accountRepository.existsByEmail(username)) {
            throw new AccountNotFoundException("Sender by id=" + username + " not found");
        } else if (!accountRepository.existsById(receiverId)) {
            throw new AccountNotFoundException("Receiver by id=" + receiverId + " not found");
        }

        offset = offset != null ? offset : 0;
        limit = limit != null ? limit : 1;

        Page<Message> page = messageRepository.findAll(
                PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "date")));

        if (page.hasContent()) {
            return page.getContent();
        } else {
            throw new MessageNotFoundException("Not messages found");
        }
    }

    @Override
    public Message getMessage(UUID id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return message.get();
        } else {
            throw new MessageNotFoundException("Message by id=" + id + " not found");
        }
    }

    @Override
    public Message sendMessage(String text, UUID senderId, UUID receiverId) {
        if (!accountRepository.existsById(senderId)) {
            throw new AccountNotFoundException("Sender by id=" + senderId + " not found");
        } else if (!accountRepository.existsById(receiverId)) {
            throw new AccountNotFoundException("Receiver by id=" + receiverId + " not found");
        } else if (text == null || text.equals("")) {
            throw new IllegalArgumentException();
        } else {
            Message message = new Message();
            message.setText(text);
            message.setSender(new Account(senderId, null, null, null));
            message.setReceiver(new Account(receiverId, null, null, null));
            message.setDate(new Date());
            messageRepository.save(message);
            return message;
        }
    }

    @Override
    public Message sendMessage(Message message) throws AccountNotFoundException, IllegalArgumentException {
        if (!accountRepository.existsById(message.getSender())) {
            throw new AccountNotFoundException("Sender by id=" + message.getSender() + " not found");
        } else if (!accountRepository.existsById(message.getReceiver())) {
            throw new AccountNotFoundException("Receiver account by id=" + message.getReceiver() + " not found");
        } else if (message.getText() == null || message.getText().equals("")) {
            throw new IllegalArgumentException();
        } else {
            Message senderMessage = new Message();
            senderMessage.setText(message.getText());
            senderMessage.setSender(new Account(message.getSender(), null, null, null));
            senderMessage.setReceiver(new Account(message.getReceiver(), null, null, null));
            senderMessage.setDate(new Date());
            messageRepository.save(senderMessage);
            return senderMessage;
        }
    }

    @Override
    public Message updateMessage(UUID id, String text) {
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException();
        } else {
            Optional<Message> message = messageRepository.findById(id);
            if (message.isPresent()) {
                message.get().setText(text);
                return message.get();
            } else {
                throw new MessageNotFoundException("Message by id=" + id + " not found");
            }
        }
    }

    @Override
    public void deleteMessage(UUID id) {
        if (!messageRepository.existsById(id)) {
            throw new MessageNotFoundException("Message by id=" + id + " not found");
        } else {
            messageRepository.deleteById(id);
        }
    }
}
