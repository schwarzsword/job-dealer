package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.BadParameterException;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.exceptions.SkillNotFoundException;
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
    @PostMapping(value = "/my/vacancies")
    public ResponseEntity<?> createVacancy(@RequestParam String name, @RequestParam String description,
                                           @RequestParam int money, @RequestParam List<String> requestedSkills,
                                           @AuthenticationPrincipal User user) {
        try {
            Vacancy vacancy = vacancyService.addVacancy(name, description, money, requestedSkills, user.getUsername());
            return ResponseEntity.ok(
                    mapper.map(vacancy, VacancyDto.class));
        } catch (CompanyNotFoundException ex) {
            return ResponseEntity.status(401).body("You have no permission to create vacancies");
        }
    }

    @Secured("ROLE_COMPANY")
    @DeleteMapping(value = "/my/vacancies/{vacancyId}")
    public ResponseEntity<?> deleteVacancy(@PathVariable("vacancyId") UUID vacancyId,
                                           @AuthenticationPrincipal User user) {
        try {
            Account byEmail = accountService.getByEmail(user.getUsername());
            Company byAccount = companyService.getByAccount(byEmail);
            vacancyService.remove(vacancyId, byAccount);
            return ResponseEntity.noContent().build();
        } catch (CompanyNotFoundException | NoPermissionException ex) {
            return ResponseEntity.status(401).body("You have no permission to create vacancies");
        }
    }


    @Secured("ROLE_COMPANY")
    @GetMapping("/my")
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
                                          @RequestParam(required = false) Integer salary,
                                          @RequestParam(required = false) List<String> skills,
                                          @RequestParam(required = false) String resumeName,
                                          @RequestParam(required = false) Integer sortBy,
                                          @RequestParam(required = false) Boolean sortAsc) {
        try {
            List<Vacancy> vacancies = vacancyService.sortAndReturn(skills, salary, resumeName, offset, limit, sortBy, sortAsc);
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

    @GetMapping(value = "vacancies/size")
    public ResponseEntity<?> getSize() {
        return ResponseEntity.ok(vacancyService.getSize());
    }
}
