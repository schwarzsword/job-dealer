package edu.netcracker.jobdealer.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table
@NoArgsConstructor
public class Skills {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "skill", unique = true)
    private String name;

    public Skills(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skills)) return false;
        Skills skills = (Skills) o;
        return id.equals(skills.id) &&
                name.equals(skills.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
