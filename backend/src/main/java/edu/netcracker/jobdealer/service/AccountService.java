package edu.netcracker.jobdealer.service;


import edu.netcracker.jobdealer.entity.Account;

public interface AccountService {

    Account getByEmail(String email);

    Account getByLogin(String login);
}
