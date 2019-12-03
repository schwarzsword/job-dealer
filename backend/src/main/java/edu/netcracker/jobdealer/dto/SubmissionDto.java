package edu.netcracker.jobdealer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class SubmissionDto {
    private UUID id;
    private UUID taskId;
    private UUID applicantId;
    private String fileData;
}
