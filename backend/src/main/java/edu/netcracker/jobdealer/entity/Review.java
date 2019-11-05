package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;
    @Basic
    @Column(name = "description")
    private String text;
    @Basic
    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "reviewsAsSource", referencedColumnName = "id")
    private Account reviewSource;

    @ManyToOne
    @JoinColumn(name = "reviewsAsDest", referencedColumnName = "id")
    private Account reviewDest;

    @ManyToMany
    @JoinTable(
            name = "reviewAccount",
            joinColumns = @JoinColumn(name = "reviewId"),
            inverseJoinColumns = @JoinColumn(name = "accountId"))
    private List<Account> increased;


    protected Review() {
    }

    public Review(String text, Account source, Account dest) {
        this.text = text;
        this.reviewSource = source;
        this.reviewDest = dest;
        this.rating = 0;
        this.increased = new ArrayList<>();
    }
}
