package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Submission;
import edu.netcracker.jobdealer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    List<Submission> findAllByTask(Task task);

    Submission findByTaskAndSubmiter_Account_Email(Task task, String email);

    void deleteByTaskAndSubmiter_Account_Email(Task task, String email);
}