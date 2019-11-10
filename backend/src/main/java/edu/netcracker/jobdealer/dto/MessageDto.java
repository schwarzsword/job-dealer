package edu.netcracker.jobdealer.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class MessageDto {
    private UUID id;
    private String text;
    private Date date;
    private String sender;
}