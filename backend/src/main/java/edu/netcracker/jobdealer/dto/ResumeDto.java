package edu.netcracker.jobdealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {

    private UUID id;

    private String name;
    private String firstName;
    private String lastName;
    private String middleName;
    private int salary;
    private String avatarUrl;
    private String about;

    private UUID applicantId;

}
