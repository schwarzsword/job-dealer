package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.AccountDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.BadParameterException;
import edu.netcracker.jobdealer.exceptions.EmailExistsException;
import edu.netcracker.jobdealer.service.AccountService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/accounts")
    public ResponseEntity signUpAccount(@RequestParam("email") String email,
                                        @RequestParam("password") String password,
                                        @RequestParam("isCompany") boolean isCompany) {
        try {
            String role = isCompany ? "ROLE_COMPANY" : "ROLE_USER";
            AccountDto account = mapper.map(accountService.addAccount(email, password, role),
                    AccountDto.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (EmailExistsException | BadParameterException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity updateAccount(@RequestParam UUID id,
                                        @RequestParam String email, @RequestParam String password) {
        try {
            AccountDto account = mapper.map(accountService.updateAccount(id, email, password),
                    AccountDto.class);
            //TODO испрвить на на билдер
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping
    public ResponseEntity deleteAccountById(@PathVariable String id) {
        accountService.deleteAccount(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my/accounts")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal User user) {
        Account byEmail = accountService.getByEmail(user.getUsername());
        return ResponseEntity.ok(mapper.map(byEmail, AccountDto.class));
    }
}
