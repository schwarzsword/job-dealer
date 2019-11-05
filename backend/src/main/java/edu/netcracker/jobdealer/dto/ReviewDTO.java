package edu.netcracker.jobdealer.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ReviewDTO {
    private UUID id;
    private String text;
    private Integer rating;
    private String sender;
}