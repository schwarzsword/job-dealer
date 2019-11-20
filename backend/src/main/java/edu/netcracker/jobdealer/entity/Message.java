package edu.netcracker.jobdealer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table
@NoArgsConstructor
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

    public Message(String text, Account source, Account dest) {
        this.text = text;
        this.messageSource = source;
        this.messageDest = dest;
        this.date = new Date();
    }

    @Mapping("sender")
    public String getSender() {
        return messageDest.getUsername();
    }

}
