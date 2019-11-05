package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.AccountDto;
import edu.netcracker.jobdealer.entity.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<AccountDto> getAllAccounts();
    AccountDto getAccountById(UUID id);
    Account getByEmail(String email);
}
