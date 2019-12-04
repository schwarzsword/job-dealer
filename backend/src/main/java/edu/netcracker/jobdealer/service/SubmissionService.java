package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Submission;
import edu.netcracker.jobdealer.entity.Task;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.UUID;

public interface SubmissionService {

    List<Applicant> showSubmitters(UUID taskId, String email);

    List<Submission> getSubmissions(UUID taskId, String email);

    Submission addSubmission(String fileData, String email, UUID taskId);

}
