package edu.netcracker.jobdealer.exceptions;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException() {
        super("Account not found");
    }

    public CompanyNotFoundException(String companyName) {
        super("Account with email: " + companyName + " not found");
    }
}
