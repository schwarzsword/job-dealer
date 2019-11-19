package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
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
@RequestMapping("/vacancies")
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
    @PostMapping(value = "/my")
    public ResponseEntity<?> createVacancy(@RequestParam String name, @RequestParam String description,
                                           @RequestParam Integer money, @RequestParam List<String> requestedSkills,
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
    @DeleteMapping(value = "/my/{vacancyId}")
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

    @GetMapping
    public ResponseEntity<?> getVacancies(@RequestParam int limit, @RequestParam int offset, @RequestParam(required = false) Integer salary, @RequestParam List<String> skills) {
        try {
            List<Vacancy> vacancies = vacancyService.applyConditions(skills, salary);
            List<Vacancy> page = vacancyService.getPage(vacancies, offset, limit);
            if (page != null) {
                return ResponseEntity.ok(page.stream()
                        .map(e -> mapper.map(e, VacancyDto.class))
                        .collect(Collectors.toList()));
            } else return ResponseEntity.noContent().build();
        } catch (SkillNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }
}
