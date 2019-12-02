package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.TaskDto;
import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.service.TaskService;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final Mapper mapper;

    public TaskController(TaskService taskService, Mapper mapper) {
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @Secured("ROLE_COMPANY")
    @PostMapping("/vacancies/{id}/task")
    public ResponseEntity<?> createOrUpdateTask(@RequestParam String taskData, @AuthenticationPrincipal User user) {
        Task task = taskService.createOrUpdateTask(taskData, user.getUsername());
        return ResponseEntity.ok(mapper.map(task, TaskDto.class));
    }

    @GetMapping("/vacancies/{id}/task")
    public ResponseEntity<?> getTask(@PathVariable UUID id) {
        Task task = taskService.getTaskByVacancyId(id);
        return ResponseEntity.ok(mapper.map(task, TaskDto.class));
    }
}
