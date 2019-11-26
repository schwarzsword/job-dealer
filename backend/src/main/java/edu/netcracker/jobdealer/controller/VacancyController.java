package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.*;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.CompanyService;
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
    private final CompanyService companyService;
    private final AccountService accountService;

    @Autowired
    public VacancyController(VacancyService vacancyService, Mapper mapper,
                             CompanyService companyService, AccountService accountService) {
        this.vacancyService = vacancyService;
        this.mapper = mapper;
        this.companyService = companyService;
        this.accountService = accountService;
    }

    @Secured("ROLE_COMPANY")
    @PostMapping(value = "/vacancies")
    public ResponseEntity<?> createOrUpdateVacancy(@RequestParam String name, @RequestParam String description,
                                                   @RequestParam int money, @RequestParam List<String> requestedSkills,
                                                   @AuthenticationPrincipal User user,
                                                   @RequestParam(required = false) String id) {
        try {
            Vacancy vacancy = vacancyService.addOrUpdateVacancy(name, description, money, requestedSkills, user.getUsername(), id);
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
    public ResponseEntity<?> getVacancies(@RequestParam int limit,
                                          @RequestParam int offset,
                                          @RequestParam(required = false) Integer money,
                                          @RequestParam(required = false) List<String> requestedSkills,
                                          @RequestParam(required = false) String vacancyName,
                                          @RequestParam(required = false) String companyName,
                                          @RequestParam(required = false) String sortBy) {
        try {
            List<Vacancy> vacancies = vacancyService.sortAndReturn(requestedSkills, money, vacancyName, companyName, offset, limit, sortBy);
            if (vacancies != null) {
                return ResponseEntity.ok(vacancies.stream()
                        .map(e -> mapper.map(e, VacancyDto.class))
                        .collect(Collectors.toList()));
            } else return ResponseEntity.noContent().build();
        } catch (SkillNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (BadParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/vacancies/size")
    public ResponseEntity<?> getSize(@RequestParam(required = false) Integer money,
                                     @RequestParam(required = false) List<String> requestedSkills,
                                     @RequestParam(required = false) String vacancyName,
                                     @RequestParam(required = false) String companyName) {
        try {
            return ResponseEntity.ok(vacancyService.getSize(requestedSkills, money, vacancyName, companyName));
        } catch (BadParameterException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
