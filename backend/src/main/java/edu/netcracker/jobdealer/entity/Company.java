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
public class Company {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "isVerified")
    private Boolean verified;

    @Column(name = "description")
    private String description;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @OneToOne
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private Account account;

    @OneToMany
    private List<Vacancy> vacancies;

    public Company(UUID id, String name, Boolean verified, String description, String avatarUrl, Account account) {
        this.id = id;
        this.name = name;
        this.verified = verified;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.account = account;
    }

    public Company(String name, Boolean verified, String description, String avatarUrl, Account account) {
        this.name = name;
        this.verified = verified;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.account = account;
    }

    @Mapping("accountId")
    public UUID getAccountId() {
        return this.account.getId();
    }
}
