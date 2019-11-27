package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.MessageDto;
import edu.netcracker.jobdealer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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

    @GetMapping("/messages")
    public List<MessageDto> getMessages(@RequestParam UUID senderId, @RequestParam UUID receiverId,
                                     @Nullable @RequestParam Integer offset, @Nullable @RequestParam Integer limit) {
        List<MessageDto> messages = messageService.getMessages(senderId, receiverId, offset, limit).stream()
                .map(message -> mapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
        log.debug(messages.toString());
        return messages;
    }

    @GetMapping("/messages/{id}")
    public MessageDto getMessage(@PathVariable UUID id) {
        MessageDto message =  mapper.map(messageService.getMessage(id), MessageDto.class);
        log.debug(message.toString());
        return message;
    }

    @PostMapping("/messages")
    public MessageDto sendMessage(@RequestParam String text, @RequestParam UUID senderId,
                                  @RequestParam UUID receiverId) {
        MessageDto message = mapper.map(messageService.sendMessage(text, senderId, receiverId), MessageDto.class);
        log.debug(message.toString());
        return message;
    }

    @PutMapping("/message")
    public MessageDto updateMessage(@RequestParam UUID id, @RequestParam String text) {
        MessageDto message = mapper.map(messageService.updateMessage(id, text), MessageDto.class);
        log.debug(message.toString());
        return message;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable UUID id) {
        messageService.deleteMessage(id);
        log.debug("User by id=" + id + " was deleted");
        return ResponseEntity.ok().build();
    }
}
