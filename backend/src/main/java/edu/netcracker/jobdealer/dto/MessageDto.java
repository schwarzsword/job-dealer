package edu.netcracker.jobdealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private UUID id;
    private String text;
    private Timestamp date;
    private String status;
    private UUID senderId;
    private UUID receiverId;
}
