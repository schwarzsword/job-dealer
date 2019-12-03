package edu.netcracker.jobdealer.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table
@NoArgsConstructor
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "vacancy", referencedColumnName = "id", nullable = false)
    private Vacancy vacancy;

    @OneToMany(mappedBy = "task")
    private List<Submission> submissions;

    public Task(String name, String description, Vacancy vacancy) {
        this.description = description;
        this.name = name;
        this.vacancy = vacancy;
    }

    @Mapping("vacancyId")
    public UUID getTaskVacancyId() {
        return vacancy.getId();
    }

}
