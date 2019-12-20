package edu.netcracker.jobdealer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "text")
    private String text;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "id")
    private Account receiver;

    @Mapping("senderId")
    public UUID getSenderId() {
        return sender.getId();
    }

    @Mapping("receiverId")
    public UUID getReceiverId() {
        return receiver.getId();
    }
}
