package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.TaskDto;
import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.TaskNotFoundException;
import edu.netcracker.jobdealer.exceptions.VacancyNotFoundException;
import edu.netcracker.jobdealer.repository.TestTaskRepository;
import edu.netcracker.jobdealer.repository.VacancyRepository;
import edu.netcracker.jobdealer.service.JsonService;
import edu.netcracker.jobdealer.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class TaskServiceImpl implements TaskService {

    private final TestTaskRepository testTaskRepository;

    private final VacancyRepository vacancyRepository;
    private final JsonService jsonService;

    public TaskServiceImpl(TestTaskRepository testTaskRepository, VacancyRepository vacancyRepository, JsonService jsonService) {
        this.testTaskRepository = testTaskRepository;
        this.vacancyRepository = vacancyRepository;
        this.jsonService = jsonService;
    }


    @Override
    public Task createOrUpdateTask(String taskData, String email, UUID vacancyId) {
        TaskDto taskDto = jsonService.parseTaskDto(taskData);
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(VacancyNotFoundException::new);
        if (taskDto.getId() == null) {
            Task task = new Task(taskDto.getName(), taskDto.getDescription(), vacancy);
            return testTaskRepository.save(task);
        } else {
            Task task = testTaskRepository.findById(taskDto.getId()).orElseThrow(TaskNotFoundException::new);
            task.setName(taskDto.getName());
            task.setDescription(taskDto.getDescription());
            return testTaskRepository.save(task);
        }
    }

    @Override
    public Task getTaskByVacancy(Vacancy vacancy) {
        return testTaskRepository.findByVacancy(vacancy).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public Task getTaskByVacancyId(UUID id) {
        return testTaskRepository.findByVacancyId(id).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public Task getTaskById(UUID taskId) {
        return testTaskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }
}
