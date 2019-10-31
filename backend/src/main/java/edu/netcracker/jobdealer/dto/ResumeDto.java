package edu.netcracker.jobdealer.dto;

import edu.netcracker.jobdealer.entity.SkillToOwner;
import lombok.Data;

import java.util.List;

@Data
public class ResumeDto {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer salary;
    private String photoLink;
    private String about;
    private List<SkillToOwner> skills;


    private ResumeDto() {
    }
}
