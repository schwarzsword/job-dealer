package edu.netcracker.jobdealer.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
public class Skills {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    @Column(name = "skill", unique = true)
    private String name;

    @OneToMany(mappedBy = "skill")
    List<SkillToOwner> owners;

    protected Skills() {
    }

    public Skills(String name) {
        this.name = name;
    }
}
