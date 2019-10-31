package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "persistent_logins", schema = "public", catalog = "netcracker")
public class PersistentLoginsEntity {
    @Basic
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    @Id
    @Column(name = "series", nullable = false, length = 64)
    private String series;
    @Basic
    @Column(name = "token", nullable = false, length = 64)
    private String token;
    @Basic
    @Column(name = "last_used", nullable = false)
    private Timestamp lastUsed;
}