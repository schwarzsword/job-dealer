package edu.netcracker.jobdealer.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class Skills {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
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
