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

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "middleName")
    private String middleName;

    @OneToMany(mappedBy = "owner")
    private List<Resume> ownedResumes;

    @ManyToMany(mappedBy = "respondents")
    List<Vacancy> responsedVacancies;

    @ManyToMany(mappedBy = "submiter")
    List<Submission> ownedSubmissions;

    @OneToOne
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private Account account;

    @Mapping("accountId")
    public UUID getAccountId() {
        return account.getId();
    }
}
