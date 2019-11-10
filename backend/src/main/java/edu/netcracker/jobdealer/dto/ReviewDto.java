package edu.netcracker.jobdealer.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewDto {
    private UUID id;
    private String text;
    private Integer rating;
    private String sender;
}