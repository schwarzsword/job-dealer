package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.CompanyService;
import edu.netcracker.jobdealer.service.VacancyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vacancies/")
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
    public ResponseEntity createVacancy(@RequestParam String name, @RequestParam String description,
                                        @RequestParam Integer money, @RequestParam List<String> requestedSkills,
                                        @AuthenticationPrincipal User user) {
        try {
            Account byEmail = accountService.getByEmail(user.getUsername());
            Company byAccount = companyService.getByAccount(byEmail);
            vacancyService.addVacancy(name, description, money, requestedSkills, byAccount);
        } catch (CompanyNotFoundException ex) {
            return ResponseEntity.status(401).body("You have no permission to create vacancies");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured("ROLE_COMPANY")
    @DeleteMapping(value = "/my/{vacancyId}")
    public ResponseEntity deleteVacancy(@PathVariable("vacancyId") UUID vacancyId,
                                        @AuthenticationPrincipal User user) {
        try {
            Account byEmail = accountService.getByEmail(user.getUsername());
            Company byAccount = companyService.getByAccount(byEmail);
            vacancyService.remove(vacancyId, byAccount);
        } catch (CompanyNotFoundException | AccountNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (NoPermissionException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(value = "/{page}")
    public ResponseEntity getAllVacanciesPage(@PathVariable("page") Integer page) {
        List<Vacancy> vacancies = vacancyService.getAll();
        int begin = --page * 10;
        int end = begin + 9;
        if (vacancies.size() <= begin) {
            return ResponseEntity.badRequest().body("There are no more vacancies");
        } else {
            if (vacancies.size() > end) {
                return ResponseEntity.ok().body(vacancies.subList(begin, vacancies.size() - 1));
            } else return ResponseEntity.ok().body(vacancies.subList(begin, end));
        }

    }

    @GetMapping
    public ResponseEntity getAllVacancies() {
        List<VacancyDto> vacancies = vacancyService.getAll()
                .stream()
                .map(e -> mapper.map(e, VacancyDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(vacancies);
    }

    @Secured("ROLE_COMPANY")
    @GetMapping("/my")
    public ResponseEntity getAllCompanyVacancies(@AuthenticationPrincipal User user) {
        List<VacancyDto> vacancies = vacancyService.getVacanciesByCompanyEmail(user.getUsername())
                .stream()
                .map(e -> mapper.map(e, VacancyDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(vacancies);
    }
}
