package edu.netcracker.jobdealer.entity;

import lombok.Data;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class Resume {

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

    @Column(name = "middleName")
    private String middleName;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @Column(name = "about")
    private String about;

//    @OneToMany(mappedBy = "owner")
//    List<SkillToOwner> skills;

    @ManyToOne
    @JoinColumn(name = "applicantId", referencedColumnName = "id")
    private Applicant applicant;

    protected Resume() {
    }

    public Resume(Applicant applicant) {
        this.applicant = applicant;
    }

    public Resume(String name, String firstName, String lastName, String middleName, Integer salary, String avatarUrl,
                  String about) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.salary = salary;
        this.avatarUrl = avatarUrl;
        this.about = about;
    }

    @Mapping("applicantId")
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
