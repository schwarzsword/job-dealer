package edu.netcracker.jobdealer.service;


import edu.netcracker.jobdealer.entity.Account;

import java.util.UUID;

public interface AccountService {

    Account getByEmail(String email);

    Account getByLogin(String login);

    Account getById(UUID userId);
}
