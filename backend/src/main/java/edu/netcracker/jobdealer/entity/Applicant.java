package edu.netcracker.jobdealer.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class Applicant {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private Account account;
    @OneToMany(mappedBy = "owner")
    private List<Resume> ownedResumes;

    @ManyToMany(mappedBy = "respondents")
    private List<Vacancy> responsedVacancies;

    @ManyToMany(mappedBy = "submiter")
    private List<Submission> ownedSubmissions;

    protected Applicant() {
    }

    public Applicant(Account account) {
        this.account = account;
    }

}