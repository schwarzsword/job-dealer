package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table
public class Resume {
    @OneToMany(mappedBy = "owner")
    List<SkillToOwner> skills;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "resumeName")
    private String resumeName;
    @Basic
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "middleName")
    private String middleName;
    @Basic
    @Column(name = "vacancy")
    private String vacancy;
    @Basic
    @Column(name = "money")
    private Integer money;
    @Basic
    @Column(name = "avatarUrl")
    private String avatarUrl;
    @Basic
    @Column(name = "about")
    private String about;
    @ManyToOne
    @JoinColumn(name = "ownedResumes", referencedColumnName = "id")
    private Applicant owner;

    protected Resume() {
    }

    public Resume(Applicant applicant) {
        this.owner = applicant;
    }
}
