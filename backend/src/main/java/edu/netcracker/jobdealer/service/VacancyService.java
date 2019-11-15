package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.VacancyDto;

import java.util.List;
import java.util.UUID;

public interface VacancyService {

    List<VacancyDto> getAllVacancies();
    VacancyDto getVacancyById(UUID id);
}
