package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.repository.VacancyRepository;
import edu.netcracker.jobdealer.service.VacancyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VacancyServiceImpl implements VacancyService {

    private VacancyRepository vacancyRepository;
    private Mapper mapper;

    @Autowired
    public VacancyServiceImpl(VacancyRepository vacancyRepository, Mapper mapper) {
        this.vacancyRepository = vacancyRepository;
        this.mapper = mapper;
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        return vacancyRepository.findAll().stream()
                .map(vacancy -> mapper.map(vacancy, VacancyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public VacancyDto getVacancyById(UUID id) {
        return mapper.map(vacancyRepository.getOne(id), VacancyDto.class);
    }
}
