package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Submission;
import edu.netcracker.jobdealer.entity.Task;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.SubmissionRepository;
import edu.netcracker.jobdealer.repository.TestTaskRepository;
import edu.netcracker.jobdealer.service.SubmissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final TestTaskRepository testTaskRepository;

    private final SubmissionRepository submissionRepository;

    private final ApplicantRepository applicantRepository;

    public SubmissionServiceImpl(TestTaskRepository testTaskRepository, SubmissionRepository submissionRepository, ApplicantRepository applicantRepository) {
        this.testTaskRepository = testTaskRepository;
        this.submissionRepository = submissionRepository;
        this.applicantRepository = applicantRepository;
    }


    @Override
    public Submission commitSubmission(Task task, String filename, Applicant user) {
        Submission submission = new Submission(filename, task, user);
        task.getSubmissions().add(submission);
        user.getOwnedSubmissions().add(submission);
        submissionRepository.save(submission);
        testTaskRepository.save(task);
        applicantRepository.save(user);
        return submission;
    }

    @Override
    public List<Applicant> showSubmitters(Task task) {
        return task.getSubmissions().stream().map(Submission::getSubmiter).collect(Collectors.toList());
    }
}
