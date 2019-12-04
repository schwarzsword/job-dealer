package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.MessageDto;
import edu.netcracker.jobdealer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class MessageController {

    private final MessageService messageService;
    private final Mapper mapper;

    @Autowired
    public MessageController(MessageService messageService, Mapper mapper) {
        this.messageService = messageService;
        this.mapper = mapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/messages")
    public List<MessageDto> getAllMessages(@RequestParam UUID receiverId, @RequestParam int limit,
                                           @RequestParam int offset, @AuthenticationPrincipal User user) {
        List<MessageDto> messages = messageService.getMessages(
                user.getUsername(), receiverId, offset, limit).stream()
                .map(message -> mapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
        log.info("{}", user.getUsername());
        return messages;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/messages/{id}")
    public MessageDto getMessage(@PathVariable UUID id) {
        MessageDto message =  mapper.map(messageService.getMessage(id), MessageDto.class);
        log.debug("{}", message);
        return message;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/messages")
    public MessageDto sendMessage(@RequestParam String text, @RequestParam UUID senderId,
                                  @RequestParam UUID receiverId) {
        MessageDto message = mapper.map(messageService.sendMessage(text, senderId, receiverId), MessageDto.class);
        log.debug("{}", message);
        return message;
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/message")
    public MessageDto updateMessage(@RequestParam UUID id, @RequestParam String text) {
        MessageDto message = mapper.map(messageService.updateMessage(id, text), MessageDto.class);
        log.debug("{}", message);
        return message;
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable UUID id) {
        messageService.deleteMessage(id);
        log.debug("User by id={} was deleted", id);
        return ResponseEntity.ok().build();
    }
}
