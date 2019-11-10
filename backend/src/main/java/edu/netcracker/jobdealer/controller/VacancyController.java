package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;
import edu.netcracker.jobdealer.exceptions.CompanyNotFoundException;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.CompanyService;
import edu.netcracker.jobdealer.service.VacancyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class VacancyController {
    private final VacancyService vacancyService;

    private final Mapper mapper;
    private final CompanyService companyService;
    private final AccountService accountService;

    @Autowired
    public VacancyController(VacancyService vacancyService, Mapper mapper, CompanyService companyService, AccountService accountService) {
        this.vacancyService = vacancyService;
        this.mapper = mapper;
        this.companyService = companyService;
        this.accountService = accountService;
    }

    @PostMapping(value = "/{email}/vacancies/")
    public ResponseEntity createVacancy(@PathVariable("email") String email,
                                        @RequestParam String name, @RequestParam String description,
                                        @RequestParam Integer money, @RequestParam List<String> requestedSkills) {
        try {
            Account byEmail = accountService.getByEmail(email);
            Company byAccount = companyService.getByAccount(byEmail);
            vacancyService.addVacancy(name, description, money, requestedSkills, byAccount);
        } catch (CompanyNotFoundException ex) {
            return ResponseEntity.status(401).body("you have no permission to create vacancies");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(404).body("user not found");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{email}/vacancies/{vacancyId}")
    public ResponseEntity deleteVacancy(@PathVariable("email") @NotBlank @Valid String email,
                                        @PathVariable("vacancyId") @NotBlank @Valid UUID vacancyId) {
        vacancyService.remove(vacancyId);
        return ResponseEntity.status(200).build();
    }

//    @PutMapping(value = "/{email}/vacancies/{vacancyId}")
//    public ResponseEntity<?> updateVacancy(@RequestBody VacancyDto vacancyDTO) {
//        vacancyService.updateVacancy(vacancyDTO);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping(value = "/vacancies/{page}")
    public ResponseEntity getAllCompanyVacanciesPage(@PathVariable("page") Integer page) {
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

    @GetMapping(value = "/vacancies/")
    public ResponseEntity getAllCompanyVacancies() {
        List<Vacancy> vacancies = vacancyService.getAll();
        return ResponseEntity.ok().body(vacancies);
    }
}
