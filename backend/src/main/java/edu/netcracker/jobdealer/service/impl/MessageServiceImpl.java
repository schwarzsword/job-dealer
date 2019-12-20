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

import java.sql.Timestamp;
import java.util.List;
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
        return messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
    }

    @Override
    public Message sendMessage(String text, UUID senderId, UUID receiverId) {
        Account sender = accountRepository.findById(senderId).orElseThrow(AccountNotFoundException::new);
        Account receiver = accountRepository.findById(receiverId).orElseThrow(AccountNotFoundException::new);
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException();
        } else {
            Message message = new Message();
            message.setText(text);
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setDate(new Timestamp(System.currentTimeMillis()));
            messageRepository.save(message);
            return message;
        }
    }

    @Override
    public Message sendMessage(Message message) throws AccountNotFoundException, IllegalArgumentException {
        Account sender = accountRepository.findById(message.getSenderId()).orElseThrow(AccountNotFoundException::new);
        Account receiver = accountRepository.findById(message.getReceiverId()).orElseThrow(AccountNotFoundException::new);
        if (message.getText() == null || message.getText().equals("")) {
            throw new IllegalArgumentException();
        } else {
            Message senderMessage = new Message();
            senderMessage.setText(message.getText());
            senderMessage.setSender(sender);
            senderMessage.setReceiver(receiver);
            senderMessage.setDate(new Timestamp(System.currentTimeMillis()));
            messageRepository.save(senderMessage);
            return senderMessage;
        }
    }

    @Override
    public Message updateMessage(UUID id, String text) {
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException();
        } else {
            Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
            message.setText(text);
            return message;
        }
    }

    @Override
    public Message changeStatus(UUID id, String status) {
        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        message.setStatus(status);
        return message;
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
