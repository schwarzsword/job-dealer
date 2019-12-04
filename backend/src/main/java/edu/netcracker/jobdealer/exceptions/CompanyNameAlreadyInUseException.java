package edu.netcracker.jobdealer.exceptions;

public class CompanyNameAlreadyInUseException extends AlreadyInUseException {
    public CompanyNameAlreadyInUseException() {
        super("This company name already in use");
    }
}
