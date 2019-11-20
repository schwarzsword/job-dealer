package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    public Account(String email, String password, String role) {
        this.username = email;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Account(UUID id, String email, String password, String role) {
        this.id = id;
        this.username = email;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
