package edu.netcracker.jobdealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private UUID id;
    private String status;
    private UUID applicantId;
    private UUID vacancyId;
    private String applicantName;
    private String vacancyName;
    private String vacancyCompanyName;
}
