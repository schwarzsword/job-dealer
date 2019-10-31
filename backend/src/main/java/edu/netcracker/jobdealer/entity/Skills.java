package edu.netcracker.jobdealer.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "skills", schema = "public", catalog = "netcracker")
public class Skills {
    @OneToMany(mappedBy = "skill")
    List<SkillToOwner> owners;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "skill", unique = true)
    private String name;

    protected Skills() {
    }

    public Skills(String name) {
        this.name = name;
    }
}
