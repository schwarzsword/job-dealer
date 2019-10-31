package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "review", schema = "public", catalog = "netcracker")
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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


    protected Review() {
    }

    public Review(String text, Account source, Account dest) {
        this.text = text;
        this.reviewSource = source;
        this.reviewDest = dest;
        this.rating = 0;
    }
}
