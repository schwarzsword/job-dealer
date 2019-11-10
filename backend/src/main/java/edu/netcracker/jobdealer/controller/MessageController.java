package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.MessageDto;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.MessageNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoRightsException;
import edu.netcracker.jobdealer.service.MessageService;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class MessageController {

    private final Mapper mapper;

    private final MessageService messageService;

    public MessageController(MessageService messageService, Mapper mapper) {
        this.messageService = messageService;
        this.mapper = mapper;
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @GetMapping(value = "{email}/messages")
    public ResponseEntity getUserMessages(@PathVariable("email") String email, @AuthenticationPrincipal User user) {
        if (user.getUsername().equals(email)) {
            List<Message> userMessages = messageService.getUserMessages(email);
            List<MessageDto> dtos = userMessages.stream().map(e -> mapper.map(e, MessageDto.class)).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } else return ResponseEntity.badRequest().body("You have no permission to read this message");
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @GetMapping(value = "{email}/messages/{mesId}")
    public ResponseEntity getMessage(@PathVariable("email") String email, @PathVariable("mesId") UUID mesId, @AuthenticationPrincipal User user) {
        Message message = messageService.getMessage(mesId);
//        MessageDTO messageDTO = mapper.map(message, MessageDTO.class);
        try {
            if (message.getMessageDest().getEmail().equals(user.getUsername())) {
                return ResponseEntity.ok(message);
            } else return ResponseEntity.badRequest().body("You have no permission to read this message");
        } catch (MessageNotFoundException e) {
            return ResponseEntity.badRequest().body("No message found");
        }
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @PostMapping(value = "{email}/messages")
    public ResponseEntity sendMessage(@PathVariable("email") String from, @RequestParam String who, @RequestParam String text, @AuthenticationPrincipal User user) {
        messageService.sendMessage(text, user.getUsername(), who);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @DeleteMapping(value = "{email}/messages/{mesId}")
    public ResponseEntity deleteMessage(@PathVariable("email") String email, @PathVariable("mesId") UUID mesId, @AuthenticationPrincipal User user) {
        try {
            messageService.deleteMessage(mesId, user.getUsername());
        } catch (NoRightsException ex) {
            return ResponseEntity.badRequest().body("You have no permission to delete this message");
        } catch (MessageNotFoundException e) {
            return ResponseEntity.badRequest().body("No message found");
        }
        return ResponseEntity.ok().build();
    }
}
