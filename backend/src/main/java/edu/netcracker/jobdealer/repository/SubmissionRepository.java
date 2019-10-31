package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Submission;
import edu.netcracker.jobdealer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByTask(Task task);
}