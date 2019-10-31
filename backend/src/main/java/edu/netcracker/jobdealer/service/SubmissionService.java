package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Submission;
import edu.netcracker.jobdealer.entity.Task;

import java.util.List;

public interface SubmissionService {

    Submission commitSubmission(Task task, String filename, Applicant user);

    List<Applicant> showSubmitters(Task task);

}
