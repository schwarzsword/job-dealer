package edu.netcracker.jobdealer.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }
}
