package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table
@NoArgsConstructor
public class Submission {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;

    @Lob
    @Column
    private String fileData;

    @ManyToOne
    @JoinColumn(name = "ownedSubmissions", referencedColumnName = "id")
    private Applicant submiter;

    @ManyToOne
    @JoinColumn(name = "submissions", referencedColumnName = "id")
    private Task task;


    public Submission(String fileData, Task task, Applicant submiter) {
        this.fileData = fileData;
        this.submiter = submiter;
        this.task = task;
    }


    @Mapping(value = "taskId")
    public UUID getTaskID() {
        return task.getId();
    }

    @Mapping(value = "applicantId")
    public UUID getApplicantID() {
        return submiter.getId();
    }
}
