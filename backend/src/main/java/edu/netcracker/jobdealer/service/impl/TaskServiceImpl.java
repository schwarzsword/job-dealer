package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.TaskNotFoundException;
import edu.netcracker.jobdealer.repository.TestTaskRepository;
import edu.netcracker.jobdealer.repository.VacancyRepository;
import edu.netcracker.jobdealer.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class TaskServiceImpl implements TaskService {

    private final TestTaskRepository testTaskRepository;

    private final VacancyRepository vacancyRepository;

    public TaskServiceImpl(TestTaskRepository testTaskRepository, VacancyRepository vacancyRepository) {
        this.testTaskRepository = testTaskRepository;
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public Task createTask(Vacancy vacancy, String name, String description) {
        Task task = new Task(name, description, vacancy);
        vacancy.setTask(task);
        testTaskRepository.save(task);
        vacancyRepository.save(vacancy);
        return task;
    }

    @Override
    public Task updateTaskName(Task task, String newName) {
        task.setName(newName);
        return testTaskRepository.save(task);
    }


    @Override
    public Task updateTaskDescription(Task task, String newDescription) {
        task.setDescription(newDescription);
        return testTaskRepository.save(task);
    }

    @Override
    public Task getTaskByVacancy(Vacancy vacancy) throws TaskNotFoundException {
        Optional<Task> byVacancy = testTaskRepository.findByVacancy(vacancy);
        if (byVacancy.isPresent()) {
            return byVacancy.get();
        } else throw new TaskNotFoundException("Task not found");
    }

    @Override
    public Task getTaskById(UUID taskId) throws TaskNotFoundException {
        Optional<Task> byVacancy = testTaskRepository.findById(taskId);
        if (byVacancy.isPresent()) {
            return byVacancy.get();
        } else throw new TaskNotFoundException("Task not found");
    }
}
