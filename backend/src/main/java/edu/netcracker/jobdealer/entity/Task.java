package edu.netcracker.jobdealer.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "task", referencedColumnName = "id", nullable = false)
    private Vacancy vacancy;

    @OneToMany(mappedBy = "task")
    private List<Submission> submissions;

    protected Task() {
    }

    public Task(String name, String description, Vacancy vacancy) {
        this.description = description;
        this.name = name;
        this.vacancy = vacancy;
    }

}
