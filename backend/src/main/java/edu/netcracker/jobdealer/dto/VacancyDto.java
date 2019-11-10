package edu.netcracker.jobdealer.dto;

import lombok.Data;

import java.util.List;

@Data
public class VacancyDto {

    private Long id;
    private String name;
    private String description;
    private Integer money;
    private List<String> requestedSkills;

    private VacancyDto() {
    }

}
