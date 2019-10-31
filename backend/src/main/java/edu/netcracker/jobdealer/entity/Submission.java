package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Submission {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    @Column
    private String filename;

    @ManyToOne
    @JoinColumn(name = "ownedSubmissions", referencedColumnName = "id")
    private Applicant submiter;

    @ManyToOne
    @JoinColumn(name = "submissions", referencedColumnName = "id")
    private Task task;

    protected Submission() {
    }

    public Submission(String filename, Task task, Applicant submiter) {
        this.filename = filename;
        this.submiter = submiter;
        this.task = task;
    }

}
