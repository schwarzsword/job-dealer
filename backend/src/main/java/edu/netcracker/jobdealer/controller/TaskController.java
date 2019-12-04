package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.SubmissionDto;
import edu.netcracker.jobdealer.dto.TaskDto;
import edu.netcracker.jobdealer.entity.Submission;
import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.service.SubmissionService;
import edu.netcracker.jobdealer.service.TaskService;
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
public class TaskController {

    private final TaskService taskService;
    private final SubmissionService submissionService;
    private final Mapper mapper;

    public TaskController(TaskService taskService, SubmissionService submissionService, Mapper mapper) {
        this.taskService = taskService;
        this.submissionService = submissionService;
        this.mapper = mapper;
    }

    @Secured("ROLE_COMPANY")
    @PostMapping("/vacancies/{id}/tasks")
    public ResponseEntity<?> createOrUpdateTask(@RequestParam String taskData,
                                                @AuthenticationPrincipal User user, @PathVariable UUID id) {
        Task task = taskService.createOrUpdateTask(taskData, user.getUsername(), id);
        return ResponseEntity.ok(mapper.map(task, TaskDto.class));
    }

    @GetMapping("/vacancies/{id}/tasks")
    public ResponseEntity<?> getTask(@PathVariable UUID id) {
        Task task = taskService.getTaskByVacancyId(id);
        return ResponseEntity.ok(mapper.map(task, TaskDto.class));
    }

    @Secured("ROLE_USER")
    @PostMapping("/vacancies/{id}/tasks/{taskId}")
    public ResponseEntity<?> submit(@PathVariable UUID taskId, @RequestParam String fileData,
                                    @AuthenticationPrincipal User user) {
        Submission submission = submissionService.addSubmission(fileData, user.getUsername(), taskId);
        return ResponseEntity.ok(mapper.map(submission, SubmissionDto.class));
    }

    @Secured("ROLE_COMPANY")
    @GetMapping("/vacancies/{id}/tasks/{taskId}")
    public ResponseEntity<?> getSubmissions(@PathVariable UUID taskId, @AuthenticationPrincipal User user) {
        List<Submission> submissions = submissionService.getSubmissions(taskId, user.getUsername());
        return ResponseEntity.ok(submissions.stream()
                .map(e -> mapper.map(e, SubmissionDto.class))
                .collect(Collectors.toList()));
    }
}
