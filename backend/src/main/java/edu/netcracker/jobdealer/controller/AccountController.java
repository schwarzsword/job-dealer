package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.AccountDto;
import edu.netcracker.jobdealer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(name = "/accounts")
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping(value = "/accounts/{id}")
    public AccountDto getAccountById(@PathVariable("id") UUID id) {
        return accountService.getAccountById(id);
    }
}
