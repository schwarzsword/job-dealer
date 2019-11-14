package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.BadParameterException;
import edu.netcracker.jobdealer.exceptions.EmailExistsException;
import edu.netcracker.jobdealer.exceptions.UsernameAlreadyExistsException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private String salt = BCrypt.gensalt();

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(String id) {
        Account account;

        try {
            UUID uuid = UUID.fromString(id);
            account = accountRepository.getById(uuid);
        } catch (IllegalArgumentException exception) {
            account = accountRepository.getByUsername(id);
        }

        if (accountRepository.existsById(account.getId())) {
            return account;
        } else {
            throw new UsernameNotFoundException("Account was not found");
        }
    }

    @Override
    public Account addAccount(String username, String email, String password, boolean isCompany) {
        if (accountRepository.existsByEmail(email)) {
            throw new EmailExistsException("Email is already exists");
        } else if (email == null || email.equals("")) {
            throw new BadParameterException("You passed an empty parameter");
        } else {
            String role = isCompany ? "ROLE_COMPANY" : "ROLE_USER";
            String pwd = BCrypt.hashpw(password, salt);
            Account account = new Account(username, email, pwd, role);
            accountRepository.save(account);
            return account;
        }
    }

    @Override
    public Account addAccount(String username, String email, String password, String role) {
        if (accountRepository.existsByEmail(email)) {
            throw new EmailExistsException("Email is already exists");
        } else if (email == null || email.equals("")) {
            throw new BadParameterException("You passed an empty parameter");
        } else {
            String pwd = BCrypt.hashpw(password, salt);
            Account account = new Account(username, email, pwd, role);
            accountRepository.save(account);
            return account;
        }
    }

    @Override
    public Account updateAccount(UUID id, String username, String email, String password) {
        if (accountRepository.existsById(id) && id != null) {
            Account account = accountRepository.getById(id);

            if (!account.getUsername().equals(username) && username != null && !username.equals("")) {
                if (!accountRepository.existsByUsername(username)) {
                    account.setUsername(username);
                } else {
                    throw new UsernameAlreadyExistsException("You passed an empty parameter " +
                            "or the username already exists");
                }
            }
            if (!account.getPassword().equals(password) && password != null && !password.equals("")) {
                account.setPassword(BCrypt.hashpw(password, salt));
            }
            if (!account.getEmail().equals(email) && email != null && !email.equals("")) {
                if (!accountRepository.existsByEmail(email)) {
                    account.setEmail(email);
                } else {
                    throw new EmailExistsException("You passed an empty parameter " +
                            "or the username already exists");
                }
            }
            accountRepository.save(account);
            return account;
        } else {
            throw new UsernameNotFoundException("You passed an empty parameter or the account was not found");
        }
    }

    @Override
    public ResponseEntity deleteAccount(UUID id) throws AccountNotFoundException {
        if (id == null) {
            throw new BadParameterException("You passed an empty parameter");
        }
        if (accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } else {
            throw new AccountNotFoundException("The account was not found");
        }
    }
}
