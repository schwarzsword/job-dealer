package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<Account> getAllAccounts();
    Account getAccount(String id);
    Account addAccount(String username, String email, String password, boolean isCompany);
    Account addAccount(String username, String email, String password, String role);
    Account updateAccount(UUID id, String username, String email, String password);
    ResponseEntity deleteAccount(UUID id) throws AccountNotFoundException;
}
