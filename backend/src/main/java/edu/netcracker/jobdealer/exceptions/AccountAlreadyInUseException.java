package edu.netcracker.jobdealer.exceptions;

public class AccountAlreadyInUseException extends AlreadyInUseException {
    public AccountAlreadyInUseException() {
        super("This account already in use");
    }
}
