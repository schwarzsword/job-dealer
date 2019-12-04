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
public class Resume {
    @ManyToMany
    @JoinTable(
            name = "resumeSkills",
            joinColumns = @JoinColumn(name = "resumeId"),
            inverseJoinColumns = @JoinColumn(name = "skillId"))
    List<Skills> skills;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "salary")
    private int salary;
    @Lob
    @Column(name = "fileData")
    private String fileData;
    @Column(name = "about")
    private String about;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "experience")
    private Boolean experience;
    @Column(name = "driverLicence")
    private Boolean driverLicence;
    @ManyToOne
    @JoinColumn(name = "applicantId", referencedColumnName = "id")
    private Applicant applicant;


    public Resume(Applicant applicant) {
        this.applicant = applicant;
    }

    public Resume(String name, String firstName, String lastName, int salary, String fileData,
                  String about, Applicant applicant, List<Skills> skills) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.fileData = fileData;
        this.about = about;
        this.applicant = applicant;
        this.skills = skills;
    }

    @Mapping("applicantId")
    public UUID getApplicant() {
        return this.applicant.getId();
    }

    @Mapping("skills")
    public List<String> getSkillsString() {
        return this.skills.stream()
                .map(Skills::getName)
                .collect(Collectors.toList());
    }
}
