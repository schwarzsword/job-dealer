package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@Table
@NoArgsConstructor
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

    @ManyToMany
    @JoinTable(
            name = "vacancySkills",
            joinColumns = @JoinColumn(name = "vacancyId"),
            inverseJoinColumns = @JoinColumn(name = "skillId"))
    List<Skills> requestedSkills;
    @ManyToMany
    @JoinTable(
            name = "vacancyUser",
            joinColumns = @JoinColumn(name = "vacancyId"),
            inverseJoinColumns = @JoinColumn(name = "applicantId"))
    List<Applicant> respondents;

    public Vacancy(String name, String description, Integer money, List<Skills> requestedSkills, Company owner) {
        this.name = name;
        this.description = description;
        this.money = money;
        this.requestedSkills = requestedSkills;
        this.owner = owner;
    }

    @Mapping("ownerName")
    public String getOwnerName(){
        return owner.getName();
    }

    @Mapping("requestedSkills")
    public List<String> getRequestedSkillsNames(){
        return requestedSkills.stream().map(Skills::getName).collect(Collectors.toList());
    }
}
