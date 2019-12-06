package edu.netcracker.jobdealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeFilters {
    private int limit;
    private int offset;
    private Integer minSalary;
    private Integer maxSalary;
    private List<String> skills;
    private String sortBy;
    private boolean descending;
}
