package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.service.VacancyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class VacancyController {

    private final VacancyService vacancyService;
    private final Mapper mapper;

    @Autowired
    public VacancyController(VacancyService vacancyService, Mapper mapper) {
        this.vacancyService = vacancyService;
        this.mapper = mapper;
    }

    @Secured("ROLE_COMPANY")
    @PostMapping(value = "/vacancies")
    public ResponseEntity<?> createOrUpdateVacancy(@RequestParam String vacancyData,
                                                   @AuthenticationPrincipal User user) {
        Vacancy vacancy = vacancyService.addOrUpdateVacancy(vacancyData, user.getUsername());
        return ResponseEntity.ok(
                mapper.map(vacancy, VacancyDto.class));
    }

    @Secured("ROLE_COMPANY")
    @DeleteMapping(value = "/vacancies/{vacancyId}")
    public ResponseEntity<?> deleteVacancy(@PathVariable("vacancyId") UUID vacancyId,
                                           @AuthenticationPrincipal User user) {
        vacancyService.remove(vacancyId, user.getUsername());
        return ResponseEntity.noContent().build();
    }


    @Secured("ROLE_COMPANY")
    @GetMapping("/my/vacancies")
    public ResponseEntity<?> getAllCompanyVacancies(@AuthenticationPrincipal User user) {
        List<VacancyDto> vacancies = vacancyService.getVacanciesByCompanyEmail(user.getUsername())
                .stream()
                .map(e -> mapper.map(e, VacancyDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(vacancies);
    }


    @GetMapping(value = "/vacancies")
    public ResponseEntity<?> getVacancies(@RequestParam String filters) {
        List<Vacancy> vacancies = vacancyService.sortAndReturn(filters);
        return ResponseEntity.ok(vacancies.stream()
                .map(e -> mapper.map(e, VacancyDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/vacancies/size")
    public ResponseEntity<?> getSize(@RequestParam String filters) {
        return ResponseEntity.ok(vacancyService.getSize(filters));
    }

    @GetMapping(value = "/vacancies/{id}")
    public ResponseEntity<?> getVacancy(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.map(vacancyService.getVacancy(id), VacancyDto.class));
    }

}
