package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;

import edu.netcracker.jobdealer.exceptions.AccountByEmailNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<Account> getAllAccounts();

    Account getByEmail(String email) throws AccountByEmailNotFoundException;

    public Account getById(UUID id) throws AccountByIdNotFoundException;

    Account signIn(String email, String password) throws UsernameNotFoundException;

    Account signUp(String password, String mail, String role) throws UsernameNotFoundException;

    Account getUserByEmail(String email) throws UsernameNotFoundException;

    Account changePassword(String email, String newPass) throws UsernameNotFoundException;
    Account getAccount(String id);

    Account addAccount(String username, String email, String password, String role);
    Account updateAccount(UUID id, String username, String email, String password);
    ResponseEntity deleteAccount(UUID id) throws AccountNotFoundException;

}
