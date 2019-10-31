package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface RegistrationService {
    Account signIn(String email, String password) throws UsernameNotFoundException;

    Account signUp(String password, String mail, String role) throws UsernameNotFoundException;

    Account getUserByEmail(String email) throws UsernameNotFoundException;

    Account changePassword(String email, String newPass) throws UsernameNotFoundException;

}
