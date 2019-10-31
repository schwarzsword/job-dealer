package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.service.RegistrationService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {
    private final AccountRepository accountRepository;
    private String salt = BCrypt.gensalt();

    public RegistrationServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
            return user;
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