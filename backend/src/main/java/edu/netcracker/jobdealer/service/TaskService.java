package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.entity.Vacancy;

import java.util.UUID;

public interface TaskService {
    Task createOrUpdateTask(String taskData, String email);

    Task getTaskByVacancy(Vacancy vacancy);
    Task getTaskByVacancyId(UUID id);

    Task getTaskById(UUID taskId);
}
