package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Submission;
import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.exceptions.TaskNotFoundException;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.SubmissionRepository;
import edu.netcracker.jobdealer.repository.TestTaskRepository;
import edu.netcracker.jobdealer.service.ResponseService;
import edu.netcracker.jobdealer.service.SubmissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final TestTaskRepository testTaskRepository;

    private final SubmissionRepository submissionRepository;

    private final ApplicantRepository applicantRepository;

    private final ResponseService responseService;

    public SubmissionServiceImpl(TestTaskRepository testTaskRepository, SubmissionRepository submissionRepository, ApplicantRepository applicantRepository, ResponseService responseService) {
        this.testTaskRepository = testTaskRepository;
        this.submissionRepository = submissionRepository;
        this.applicantRepository = applicantRepository;
        this.responseService = responseService;
    }

    @Override
    public List<Applicant> showSubmitters(UUID taskId, String email) {
        Task task = testTaskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        if (task.getVacancy().getOwner().getAccount().getEmail().equals(email)) {
            return submissionRepository.findAllByTask(task)
                    .stream()
                    .map(Submission::getSubmiter)
                    .collect(Collectors.toList());
        } else throw new NoPermissionException();
    }

    @Override
    public List<Submission> getSubmissions(UUID taskId, String email) {
        Task task = testTaskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        if (task.getVacancy().getOwner().getAccount().getEmail().equals(email)) {
            return submissionRepository.findAllByTask(task);
        } else throw new NoPermissionException();
    }

    @Override
    public Submission addSubmission(byte[] fileData, String email, UUID taskId) {

        Applicant applicant = applicantRepository.findByAccountEmail(email).orElseThrow(ApplicantNotFoundException::new);
        Task task = testTaskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        responseService.apply(task.getTaskVacancyId(), email);
        Submission save = submissionRepository.save(new Submission(fileData, task, applicant));
        applicant.addSubmission(save);
        task.addSubmission(save);
        applicantRepository.save(applicant);
        testTaskRepository.save(task);
        return save;
    }
}
