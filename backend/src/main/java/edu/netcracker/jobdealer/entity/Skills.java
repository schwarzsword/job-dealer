package edu.netcracker.jobdealer.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
@NoArgsConstructor
public class Skills {
    @OneToMany(mappedBy = "skill")
    List<SkillToOwner> owners;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;
    @Basic
    @Column(name = "skill", unique = true)
    private String name;

    public Skills(String name) {
        this.name = name;
    }
}
