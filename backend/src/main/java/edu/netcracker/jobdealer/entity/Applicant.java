package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "resumeId", referencedColumnName = "id")
    private Resume activeResume;

    @OneToMany(mappedBy = "applicant")
    private List<Resume> ownedResumes;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Response> responses;

    @OneToMany(mappedBy = "submiter")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Submission> ownedSubmissions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account", referencedColumnName = "id")
    private Account account;

    public Applicant(Account account) {
        this.account = account;
    }

    @Mapping("accountId")
    public UUID getAccountId() {
        return account.getId();
    }

    public void addResponse(Response response) {
        responses.add(response);
    }

    public void addSubmission(Submission submission) {
        ownedSubmissions.add(submission);
    }
}
