package edu.netcracker.jobdealer.exceptions;

public class CompanyNotFoundException extends NotFoundException {

    public CompanyNotFoundException() {
        super("Company not found");
    }

    public CompanyNotFoundException(String msg) {
        super(msg);
    }
}
