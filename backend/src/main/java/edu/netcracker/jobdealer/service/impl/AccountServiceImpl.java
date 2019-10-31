package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.ResourceNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Account with mail " + email + " not found"));
    }

    @Override
    public Account getByLogin(String login) {
        return accountRepository.findByUsername(login).orElseThrow(() -> new ResourceNotFoundException("Account with login " + login + " not found"));
    }
}
