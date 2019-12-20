package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ResponseDto;
import edu.netcracker.jobdealer.entity.Response;
import edu.netcracker.jobdealer.service.ResponseService;
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
public class ResponseController {

    private final ResponseService responseService;
    private final Mapper mapper;

    public ResponseController(ResponseService responseService, Mapper mapper) {
        this.responseService = responseService;
        this.mapper = mapper;
    }

    @Secured("ROLE_COMPANY")
    @PostMapping("/responses/{id}")
    public ResponseEntity<?> setStatus(@PathVariable UUID id, @RequestParam String status,
                                       @AuthenticationPrincipal User user) {
        responseService.setStatus(id, status, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_USER")
    @PostMapping("/vacancies/{id}")
    public ResponseEntity<?> apply(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        responseService.apply(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_USER")
    @GetMapping("/vacancies/{id}/isApplied")
    public ResponseEntity<?> isApplied(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        return ResponseEntity.ok(responseService.isApplied(id, user.getUsername()));
    }

    @Secured("ROLE_COMPANY")
    @GetMapping("/vacancies/{id}/responses")
    public ResponseEntity<?> getRespondents(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        List<Response> responses = responseService.getRespondents(id, user.getUsername());
        return ResponseEntity.ok(responses.stream()
                .map(e -> mapper.map(e, ResponseDto.class))
                .collect(Collectors.toList()));
    }

    @Secured("ROLE_USER")
    @GetMapping("/responses/my")
    public ResponseEntity<?> getRespondents(@AuthenticationPrincipal User user) {
        List<Response> responses = responseService.getResponses(user.getUsername());
        return ResponseEntity.ok(responses.stream()
                .map(e -> mapper.map(e, ResponseDto.class))
                .collect(Collectors.toList()));
    }


}
