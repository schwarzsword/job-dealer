package edu.netcracker.jobdealer.exceptions;

public class ApiException extends RuntimeException {
    public ApiException(String msg) {
        super(msg);
    }
}
