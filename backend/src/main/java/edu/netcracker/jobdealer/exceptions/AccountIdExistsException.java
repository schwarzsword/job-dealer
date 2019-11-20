package edu.netcracker.jobdealer.exceptions;

public class AccountIdExistsException extends ExistsException {

    public AccountIdExistsException() {
        super("Account id already in use");
    }
}
