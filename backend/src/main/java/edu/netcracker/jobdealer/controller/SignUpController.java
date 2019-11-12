package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    private final RegistrationService registrationService;


    @Autowired
    public SignUpController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/signUp")
    public ResponseEntity signUp(@RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam boolean isCompany) {
        String role;
        if (isCompany) role = "ROLE_COMPANY";
        else role = "ROLE_USER";
        try {
            registrationService.signUp(password, email, role);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}