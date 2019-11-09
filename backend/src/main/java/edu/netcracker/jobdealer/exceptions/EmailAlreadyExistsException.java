package edu.netcracker.jobdealer.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }
}
