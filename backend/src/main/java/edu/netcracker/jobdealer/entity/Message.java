package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "message", schema = "public", catalog = "netcracker")
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "text")
    private String text;


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
    }
}
