package edu.netcracker.jobdealer.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
public class Applicant {
    @ManyToMany(mappedBy = "respondents")
    List<Vacancy> responsedVacancies;
    @ManyToMany(mappedBy = "submiter")
    List<Submission> ownedSubmissions;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @OneToOne
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private Account account;
    @OneToMany(mappedBy = "owner")
    private List<Resume> ownedResumes;

    protected Applicant() {
    }

    public Applicant(Account account) {
        this.account = account;
    }

}