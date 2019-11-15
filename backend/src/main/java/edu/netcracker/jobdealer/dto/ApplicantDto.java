package edu.netcracker.jobdealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private UUID accountId;
}
