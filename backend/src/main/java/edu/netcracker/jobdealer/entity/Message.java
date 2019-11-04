package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;
    @Basic
    @Column(name = "text")
    private String text;
    @Basic
    @Column(name = "date")
    private Date date;


    @ManyToOne
    @JoinColumn(name = "messagesAsSource", referencedColumnName = "id")
    private Account messageSource;

    @ManyToOne
    @JoinColumn(name = "messagesAsDest", referencedColumnName = "id")
    private Account messageDest;


    protected Message() {
    }

    public Message(String text, Account source, Account dest) {
        this.text = text;
        this.messageSource = source;
        this.messageDest = dest;
        this.date = new Date();
    }
}
