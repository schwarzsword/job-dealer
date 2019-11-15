package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.AccountDto;
import edu.netcracker.jobdealer.exceptions.BadParameterException;
import edu.netcracker.jobdealer.exceptions.EmailExistsException;
import edu.netcracker.jobdealer.service.AccountService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final Mapper mapper;

    @Autowired
    public AccountController(AccountService accountService, Mapper mapper) {
        this.accountService = accountService;
        this.mapper = mapper;
    }


    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(mapper.map(accountService.getAccount(id), AccountDto.class));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/accounts")
    public ResponseEntity signUpAccount(@RequestParam String username, @RequestParam String email,
                                        @RequestParam String password, @RequestParam boolean isCompany) {
        try {
            String role = isCompany ? "ROLE_COMPANY" : "ROLE_USER";
            AccountDto account = mapper.map(accountService.addAccount(username, email, password, role),
                    AccountDto.class);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (EmailExistsException | BadParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping(value = "/accounts")
    public ResponseEntity UpdateAccount(@RequestParam UUID id, @RequestParam String username,
                                        @RequestParam String email, @RequestParam String password) {
        try {
            AccountDto account = mapper.map(accountService.updateAccount(id, username, email, password),
                    AccountDto.class);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity deleteAccountById(@PathVariable String id) {
        try {
            accountService.deleteAccount(UUID.fromString(id));
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
