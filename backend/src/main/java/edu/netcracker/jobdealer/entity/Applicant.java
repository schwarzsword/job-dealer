package edu.netcracker.jobdealer.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "applicant", schema = "public", catalog = "netcracker")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private Account account;
    @OneToMany(mappedBy = "owner")
    private List<Resume> ownedResumes;

    @ManyToMany(mappedBy = "respondents")
    List<Vacancy> responsedVacancies;
    @ManyToMany(mappedBy = "submiter")
    List<Submission> ownedSubmissions;

    protected Applicant() {
    }

    public Applicant(Account account) {
        this.account = account;
    }

}