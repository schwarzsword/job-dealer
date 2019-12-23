package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@Table
@NoArgsConstructor
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "description", length = 1000)
    private String text;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "reviewsAsSource", referencedColumnName = "id")
    private Account reviewSource;

    @ManyToOne
    @JoinColumn(name = "reviewsAsDest", referencedColumnName = "id")
    private Account reviewDest;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "reviewAccount",
            joinColumns = @JoinColumn(name = "reviewId"),
            inverseJoinColumns = @JoinColumn(name = "accountId"))
    private List<Account> voted;


    public Review(String text, Account source, Account dest) {
        this.text = text;
        this.reviewSource = source;
        this.reviewDest = dest;
        this.rating = 0;
        this.voted = new ArrayList<>();
    }

    @Mapping(value = "sender")
    public String getSenderName() {
        return reviewSource.getUsername();
    }

    @Mapping(value = "voted")
    public List<UUID> getVotedUUID() {
        return getVoted().stream().map(Account::getId).collect(Collectors.toList());
    }
}
