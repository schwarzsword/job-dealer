package edu.netcracker.jobdealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filters {
    int limit;
    int offset;
    Integer money;
    List<String> requestedSkills;
    String vacancyName;
    String companyName;
    String sortBy;
    boolean descending;
}
