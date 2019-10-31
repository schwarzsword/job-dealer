package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class Vacancy {
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
    @Basic
    @Column(name = "money")
    private Integer money;
    @ManyToOne
    @JoinColumn(name = "vacancies", referencedColumnName = "id")
    private Company owner;
    @OneToOne(mappedBy = "vacancy")
    private Task task;

    @OneToMany(mappedBy = "id")
    List<Skills> requestedSkills;
    @ManyToMany
    @JoinTable(
            name = "vacancyUser",
            joinColumns = @JoinColumn(name = "vacancyId"),
            inverseJoinColumns = @JoinColumn(name = "applicantId"))
    List<Applicant> respondents;

    protected Vacancy() {
    }

    public Vacancy(String name, String description, Integer money, List<Skills> requestedSkills, Company owner) {
        this.name = name;
        this.description = description;
        this.money = money;
        this.requestedSkills = requestedSkills;
        this.owner = owner;
    }
}
