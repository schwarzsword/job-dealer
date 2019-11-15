package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class VacancyController {

    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping(value = "/vacancies")
    public List<VacancyDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @GetMapping(value = "/vacancies/{id}")
    public VacancyDto getVacancyById(@PathVariable UUID id) {
        return vacancyService.getVacancyById(id);
    }
}
