package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.AccountDto;
import edu.netcracker.jobdealer.service.AccountService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final Mapper mapper;

    @Autowired
    public AccountController(AccountService accountService, Mapper mapper) {
        this.accountService = accountService;
        this.mapper = mapper;
    }


    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/accounts")
    public ResponseEntity<?> signUpAccountWithRole(@RequestParam String email,
                                                   @RequestParam String password,
                                                   @RequestParam String role) {
        AccountDto account = mapper.map(accountService.signUp(password, email, role),
                AccountDto.class);
        return ResponseEntity.ok(account);
    }

}