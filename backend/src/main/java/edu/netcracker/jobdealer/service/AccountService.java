package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<Account> getAllAccounts();

    Account getByEmail(String email);

    Account getById(UUID id);

    Account getUserByEmail(String email);

    Account changePassword(String email, String newPass);

    Account getAccount(String id);

    Account addAccount(String email, String password, String role);

    Account updateAccount(UUID id, String email, String password);

    void deleteAccount(UUID id);

}

