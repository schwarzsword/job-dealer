package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.AccountByEmailNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.BadParameterException;
import edu.netcracker.jobdealer.exceptions.EmailExistsException;
import edu.netcracker.jobdealer.exceptions.UsernameAlreadyExistsException;

import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
  
  //TODO посмотреть изменения


    private final ApplicantRepository applicantRepository;
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;
    private String salt = BCrypt.gensalt();

    @Autowired

    public AccountServiceImpl(ApplicantRepository applicantRepository, CompanyRepository companyRepository, AccountRepository accountRepository) {
        this.applicantRepository = applicantRepository;
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
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

    @Override
    public Account signIn(String email, String password) {
        Account account = getUserByEmail(email);
        if (BCrypt.checkpw(password, account.getPassword())) {
            return account;
        } else throw new UsernameNotFoundException("Invalid username or password");
    }

    @Override
    public Account signUp(String password, String mail, String role)
            throws UsernameNotFoundException {
        if (!accountRepository.existsByEmail(mail)) {
            String pwd = BCrypt.hashpw(password, salt);
            Account user = new Account(pwd, mail, role);
            accountRepository.save(user);
            return accountRepository.findByEmail(mail).get();
        } else throw new UsernameNotFoundException("User is already exists");
    }

    @Override
    public Account getUserByEmail(String email) throws UsernameNotFoundException {
        Optional<Account> optionalUsersEntity = accountRepository.findByEmail(email);
        if (optionalUsersEntity.isPresent()) {
            return optionalUsersEntity.get();
        } else throw new UsernameNotFoundException("User not found");
    }

    @Override
    public Account changePassword(String email, String newPass) throws UsernameNotFoundException {
        if (accountRepository.existsByEmail(email)) {
            String pwd = BCrypt.hashpw(newPass, salt);
            Account user = getUserByEmail(email);
            user.setPassword(pwd);
            accountRepository.save(user);
            return user;
        } else throw new UsernameNotFoundException("User not found");

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
