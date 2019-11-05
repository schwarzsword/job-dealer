package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.AccountDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.ResourceNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.service.AccountService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final Mapper mapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, Mapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> mapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(UUID id) {
        return mapper.map(accountRepository.getById(id), AccountDto.class);
    }

    @Override
    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with mail " + email + " not found"));
    }
}
