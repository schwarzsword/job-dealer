package edu.netcracker.jobdealer.exceptions;

public class AccountByEmailNotFoundException extends AccountNotFoundException {
    public AccountByEmailNotFoundException(String email) {
        super("Account with id: " + email + " not found");
    }
}
