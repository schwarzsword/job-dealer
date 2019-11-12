package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.AccountDto;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.AccountByEmailNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.service.AccountService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final Mapper mapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, Mapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getByEmail(String email) throws AccountByEmailNotFoundException {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountByEmailNotFoundException(email));
    }

    @Override
    public Account getById(UUID id) throws AccountByIdNotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountByIdNotFoundException(id));
    }
}
