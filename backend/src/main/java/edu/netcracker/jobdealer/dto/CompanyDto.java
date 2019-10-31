package edu.netcracker.jobdealer.dto;

import lombok.Data;

@Data
public class CompanyDto {

    private Long id;
    private String name;
    private Boolean verified;
    private String description;
    private String avatarUrl;
    private Integer accountId;
}
