package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message sendMessage(String text, String srcEmail, UUID receiver) throws AccountNotFoundException;

    List<Message> getUserMessages(String email);


    //May be this is not necessary

//    void deleteMessage(UUID mesId, String ownerEmail) throws MessageNotFoundException, NoPermissionException;
//
//    Message getMessage(UUID mesId) throws MessageNotFoundException;
}
