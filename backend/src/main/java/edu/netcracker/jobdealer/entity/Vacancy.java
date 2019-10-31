package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "vacancy", schema = "public", catalog = "netcracker")
public class Vacancy {
    @OneToMany(mappedBy = "id")
    List<Skills> requestedSkills;
    @ManyToMany
    @JoinTable(
            name = "vacancyUser",
            joinColumns = @JoinColumn(name = "vacancyId"),
            inverseJoinColumns = @JoinColumn(name = "applicantId"))
    List<Applicant> respondents;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
