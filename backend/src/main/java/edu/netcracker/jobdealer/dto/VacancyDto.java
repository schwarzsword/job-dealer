package edu.netcracker.jobdealer.dto;

import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Skills;
import lombok.Data;

import java.util.List;

@Data
public class VacancyDto {

    private Long id;
    private List<Skills> requestedSkills;
    private String name;
    private String description;
    private int money;
    private Company owner;

    private VacancyDto() {
    }

}
