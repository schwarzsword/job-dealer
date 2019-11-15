package edu.netcracker.jobdealer.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VacancyDto {

    private UUID id;
    private String name;
    private String description;
    private Integer money;
    private List<String> requestedSkills;
    private String ownerName;

}
