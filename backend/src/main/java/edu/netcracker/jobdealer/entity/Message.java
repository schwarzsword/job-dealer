package edu.netcracker.jobdealer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.Date;
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

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "id")
    private Account receiver;

    public void setSender(UUID sender) {
        this.sender.setId(sender);
    }

    public void setReceiver(UUID receiver) {
        this.receiver.setId(receiver);
    }

    @Mapping("senderId")
    public UUID getSender() {
        return sender.getId();
    }

    @Mapping("ReceiverId")
    public UUID getReceiver() {
        return receiver.getId();
    }
}
