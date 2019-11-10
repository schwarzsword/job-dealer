package edu.netcracker.jobdealer.dto;


import edu.netcracker.jobdealer.config.Pair;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ResumeDto {

    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer salary;
    private String photoLink;
    private String about;
    private List<Pair<String, Integer>> skills;


    private ResumeDto() {
    }
}
