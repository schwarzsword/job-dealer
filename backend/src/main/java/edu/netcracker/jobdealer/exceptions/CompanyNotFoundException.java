package edu.netcracker.jobdealer.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String msg) {
        super(msg);
    }
}
