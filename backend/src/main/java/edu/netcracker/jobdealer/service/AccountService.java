package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.AccountByEmailNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<Account> getAllAccounts();

    Account getByEmail(String email) throws AccountByEmailNotFoundException;

    public Account getById(UUID id) throws AccountByIdNotFoundException;
}
