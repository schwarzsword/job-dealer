package edu.netcracker.jobdealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private UUID id;
    private String name;
    private boolean isVerified;
    private String description;
    private String fileData;
    private boolean withTask;
    private UUID accountId;
}
