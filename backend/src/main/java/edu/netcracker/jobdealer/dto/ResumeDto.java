package edu.netcracker.jobdealer.dto;

import edu.netcracker.jobdealer.entity.SkillToOwner;
import lombok.Data;

import java.util.List;

@Data
public class ResumeDto {
    String firstName;
    String middleName;
    String lastName;
    Integer salary;
    String photoLink;
    String about;
    List<SkillToOwner> skills;


    private ResumeDto() {
    }
}
