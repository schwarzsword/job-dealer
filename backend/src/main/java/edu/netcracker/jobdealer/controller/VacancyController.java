package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.CompanyService;
import edu.netcracker.jobdealer.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class VacancyController {
    private final VacancyService vacancyService;
    private final AccountService accountService;
    private final CompanyService companyService;

    @Autowired
    public VacancyController(VacancyService vacancyService,
                             AccountService accountService,
                             CompanyService companyService) {
        this.vacancyService = vacancyService;
        this.accountService = accountService;
        this.companyService = companyService;
    }

    @RequestMapping(value = "/{email}/vacancies/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanyVacancies(@PathVariable("email") @NotBlank @Valid String email) {
        Account account = accountService.getByEmail(email);
        Company company = companyService.getByAccount(account);
        List<Vacancy> companyVacancies = vacancyService.getVacanciesByCompany(company);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{email}/vacancies/", method = RequestMethod.POST)
    public ResponseEntity<?> createVacancy(@PathVariable("email") @NotBlank @Valid String email,
                                           @RequestBody Vacancy vacancy) {
        Vacancy createdVacancy = vacancyService.addVacancy(vacancy);
        return new ResponseEntity<>(createdVacancy, HttpStatus.OK);
    }

    @RequestMapping(value = "/{email}/vacancies/{vacancyId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVacancy(@PathVariable("email") @NotBlank @Valid String email,
                                           @PathVariable("vacancyId") @NotBlank @Valid long vacancyId) {
        vacancyService.remove(vacancyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{email}/vacancies/{vacancyId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateVacancy(@PathVariable("email") @NotBlank @Valid String email,
                                           @RequestBody VacancyDto vacancyDTO) {
        vacancyService.updateVacancy(vacancyDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
