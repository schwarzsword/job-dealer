package edu.netcracker.jobdealer.entity;


import lombok.Data;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table
public class SkillToOwner {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;
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

    public SkillToOwner(Resume owner, Skills skill, int level) {
        this.owner = owner;
        this.level = level;
        this.skill = skill;
    }

    @Mapping("skill")
    public String getSkills() {
        return skill.getName();
    }
}
