package edu.netcracker.jobdealer.config.security;

import edu.netcracker.jobdealer.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

@Slf4j
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private RegistrationService registrationService;

    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Account account = registrationService.getUserByEmail(login);

        if (account == null) {
            log.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole()));

        log.info("Auth attempt\n User: " + account.getEmail() + "\n Role:" + account.getRole());

        return new User(account.getEmail(), account.getPassword(), authorities);
    }

}