package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.TaskNotFoundException;

import java.util.UUID;

public interface TaskService {
    Task createTask(Vacancy vacancy, String name, String description);

    Task updateTaskName(Task task, String newName);

    Task updateTaskDescription(Task task, String newDescription);

    Task getTaskByVacancy(Vacancy vacancy) throws TaskNotFoundException;

    Task getTaskById(UUID taskId) throws TaskNotFoundException;
}
