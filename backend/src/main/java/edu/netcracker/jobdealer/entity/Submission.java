package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Basic
    @Column
    private String filename;

    @ManyToOne
    @JoinColumn(name = "ownedSubmissions", referencedColumnName = "id")
    private Applicant submiter;

    @ManyToOne
    @JoinColumn(name = "submissions", referencedColumnName = "id")
    private Task task;


    public Submission(String filename, Task task, Applicant submiter) {
        this.filename = filename;
        this.submiter = submiter;
        this.task = task;
    }

}
