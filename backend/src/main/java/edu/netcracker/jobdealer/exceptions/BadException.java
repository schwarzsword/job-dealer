package edu.netcracker.jobdealer.exceptions;

public class BadException extends ApiException {

    public BadException() {
        super("Something wrong");
    }
}
