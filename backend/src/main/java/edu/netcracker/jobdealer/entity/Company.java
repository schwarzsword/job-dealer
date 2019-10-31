package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "verified")
    private Boolean verified;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "avatarUrl")
    private String avatarUrl;

    @OneToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    private Account account;

    @OneToMany
    private List<Vacancy> vacancies;

    public Company(Account account) {
        this.account = account;
    }

    public Company(String name, Boolean verified, String description, String avatarUrl) {
        this.name = name;
        this.verified = verified;
        this.description = description;
        this.avatarUrl = avatarUrl;
    }

    public UUID getAccountId() {
        return this.account.getId();
    }
}
