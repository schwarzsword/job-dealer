package edu.netcracker.jobdealer.exceptions;

public class AccountIdAlreadyExistsException extends RuntimeException {

    public AccountIdAlreadyExistsException(String msg) {
        super(msg);
    }
}
