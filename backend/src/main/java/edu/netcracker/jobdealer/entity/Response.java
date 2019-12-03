package edu.netcracker.jobdealer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Response {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "applicant", referencedColumnName = "id")
    Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "vacancy", referencedColumnName = "id")
    Vacancy vacancy;


    @Mapping(value = "applicantId")
    public UUID getApplicantID() {
        return applicant.getId();
    }

    @Mapping(value = "vacancyId")
    public UUID getVacancyID() {
        return vacancy.getId();
    }

    @Mapping(value = "applicantName")
    public String getApplicantName() {
        return applicant.getActiveResume().getName();
    }

    @Mapping(value = "vacancyName")
    public String getVacancyName() {
        return vacancy.getName();
    }

    @Mapping(value = "vacancyCompanyName")
    public String getVacancyCompanyName() {
        return vacancy.getOwner().getName();
    }
}
