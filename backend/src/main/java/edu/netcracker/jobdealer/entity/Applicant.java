package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
public class Applicant {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "applicant")
    private List<Resume> ownedResumes;

    @ManyToMany(mappedBy = "respondents")
    private List<Vacancy> responsedVacancies;

    @OneToMany(mappedBy = "submiter")
    private List<Submission> ownedSubmissions;

//    @OneToOne
//    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
//    private Account account;

    public Applicant(Account account) {
        this.account = account;
    }

    @Mapping("accountId")
    public UUID getAccountId() {
        return account.getId();
    }
}
