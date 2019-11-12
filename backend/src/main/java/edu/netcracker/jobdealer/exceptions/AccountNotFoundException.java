package edu.netcracker.jobdealer.exceptions;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException() {
        super("Account not found");
    }

    public AccountNotFoundException(String s) {
        super(s);
    }
}
