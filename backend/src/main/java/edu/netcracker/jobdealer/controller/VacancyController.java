package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ResponseDto;
import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Response;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.service.ResponseService;
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
        try {
            Vacancy vacancy = vacancyService.addOrUpdateVacancy(vacancyData, user.getUsername());
            return ResponseEntity.ok(
                    mapper.map(vacancy, VacancyDto.class));
        } catch (CompanyNotFoundException ex) {
            return ResponseEntity.status(401).body("You have no permission to create vacancies");
        }
    }

    @Secured("ROLE_COMPANY")
    @DeleteMapping(value = "/vacancies/{vacancyId}")
    public ResponseEntity<?> deleteVacancy(@PathVariable("vacancyId") UUID vacancyId,
                                           @AuthenticationPrincipal User user) {
        try {
            vacancyService.remove(vacancyId, user.getUsername());
            return ResponseEntity.noContent().build();
        } catch (VacancyNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoPermissionException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
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
        try {
            List<Vacancy> vacancies = vacancyService.sortAndReturn(filters);
            return ResponseEntity.ok(vacancies.stream()
                    .map(e -> mapper.map(e, VacancyDto.class))
                    .collect(Collectors.toList()));
        } catch (SkillNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (BadParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/vacancies/size")
    public ResponseEntity<?> getSize(@RequestParam String filters) {
        try {
            return ResponseEntity.ok(vacancyService.getSize(filters));
        } catch (BadParameterException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/vacancies/{id}")
    public ResponseEntity<?> getVacancy(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.map(vacancyService.getVacancy(id), VacancyDto.class));
    }

}
