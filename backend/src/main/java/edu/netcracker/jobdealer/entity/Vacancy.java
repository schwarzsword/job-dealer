package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@Table
@NoArgsConstructor
public class Vacancy {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vacancySkills",
            joinColumns = @JoinColumn(name = "vacancyId"),
            inverseJoinColumns = @JoinColumn(name = "skillId"))
    private List<Skills> requestedSkills;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "money")
    private int money;
    @Column(name = "withTask")
    private boolean withTask;
    @ManyToOne
    @JoinColumn(name = "vacancies", referencedColumnName = "id")
    private Company owner;

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Response> responses;

    public Vacancy(List<Skills> requestedSkills, String name, String description, int money, boolean withTask, Company owner) {
        this.requestedSkills = requestedSkills;
        this.name = name;
        this.description = description;
        this.money = money;
        this.withTask = withTask;
        this.owner = owner;
    }

    @Mapping("ownerName")
    public String getOwnerName() {
        return owner.getName();
    }

    @Mapping("requestedSkills")
    public List<String> getRequestedSkillsNames() {
        return requestedSkills.stream().map(Skills::getName).collect(Collectors.toList());
    }

    public void addResponse(Response response) {
        responses.add(response);
    }

}
