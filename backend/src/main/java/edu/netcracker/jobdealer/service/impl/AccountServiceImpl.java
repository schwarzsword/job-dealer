package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.AccountByEmailNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.CompanyRepository;
import edu.netcracker.jobdealer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {


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
    }
}
