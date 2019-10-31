package edu.netcracker.jobdealer.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "skillOwner", schema = "public", catalog = "netcracker")
public class SkillToOwner {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "level")
    private int level;

    @ManyToOne
    @JoinColumn(name = "owners", referencedColumnName = "id")
    private Skills skill;
    @ManyToOne
    @JoinColumn(name = "skills", referencedColumnName = "id")
    private Resume owner;

    protected SkillToOwner() {
    }

    public SkillToOwner(Resume owner) {
        this.owner = owner;
    }
}
