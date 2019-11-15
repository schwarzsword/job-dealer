package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.repository.MessageRepository;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Transactional
@Service
public class MessageServiceImpl implements MessageService {


    private final AccountService accountService;

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }


    @Override
    public Message sendMessage(String text, String srcEmail, UUID receiverID) throws AccountByIdNotFoundException {
        Account src = accountService.getByEmail(srcEmail);
        Account dest = accountService.getById(receiverID);
        Message message = new Message(text, src, dest);
        messageRepository.save(message);
        return message;
    }

    @Override
    public List<Message> getUserMessages(String email) {
        return messageRepository.findAllByMessageDest_Email(email);
    }


    //May be this is not necessary

//    @Override
//    public void deleteMessage(UUID mesId, String ownerEmail) throws MessageNotFoundException, NoPermissionException {
//        Message message = getMessage(mesId);
//        if (message.getMessageDest().getEmail().equals(ownerEmail)) {
//            messageRepository.delete(message);
//        } else throw new NoPermissionException("You can delete only owned messages");
//    }
//
//    @Override
//    public Message getMessage(UUID mesId) throws MessageNotFoundException {
//        return messageRepository.findById(mesId).orElseThrow(MessageNotFoundException::new);
//    }
}
