package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.MessageDto;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.exceptions.NotFoundException;
import edu.netcracker.jobdealer.service.MessageService;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "my/messages")
    public ResponseEntity<?> getUserMessages(@AuthenticationPrincipal User user) {
        List<Message> userMessages = messageService.getUserMessages(user.getUsername());
        List<MessageDto> dtos = userMessages
                .stream()
                .map(e -> mapper.map(e, MessageDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/accounts/{id}/messages")
    public ResponseEntity<?> sendMessage(@PathVariable("id") UUID receiver,
                                         @RequestParam String text,
                                         @AuthenticationPrincipal User user) {
        try {
            messageService.sendMessage(text, user.getUsername(), receiver);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());

        }
        return ResponseEntity.noContent().build();
    }


    //May be this is not necessary

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping(value = "{email}/messages/{mesId}")
//    public ResponseEntity getMessage(@PathVariable("email") String email,
//                                     @PathVariable("mesId") UUID mesId,
//                                     @AuthenticationPrincipal User user) {
//        Message message = messageService.getMessage(mesId);
//        MessageDto messageDto = mapper.map(message, MessageDto.class);
//        try {
//            if (message.getMessageDest().getEmail().equals(user.getUsername())) {
//                return ResponseEntity.ok(messageDto);
//            } else return ResponseEntity.badRequest().body("You have no permission to read this message");
//        } catch (MessageNotFoundException e) {
//            return ResponseEntity.badRequest().body("No message found");
//        }
//    }


//    @PreAuthorize("isAuthenticated()")
//    @DeleteMapping(value = "{email}/messages/{mesId}")
//    public ResponseEntity<?> deleteMessage(@PathVariable("email") String email,
//                                           @PathVariable("mesId") UUID mesId,
//                                           @AuthenticationPrincipal User user) {
//        try {
//            messageService.deleteMessage(mesId, user.getUsername());
//        } catch (NoPermissionException ex) {
//            return ResponseEntity.badRequest().body("You have no permission to delete this message");
//        } catch (MessageNotFoundException e) {
//            return ResponseEntity.badRequest().body("No message found");
//        }
//        return ResponseEntity.noContent().build();
//    }
}
