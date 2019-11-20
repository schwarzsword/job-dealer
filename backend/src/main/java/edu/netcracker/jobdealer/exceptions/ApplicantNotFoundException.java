package edu.netcracker.jobdealer.exceptions;

public class ApplicantNotFoundException extends RuntimeException {

    public ApplicantNotFoundException() {
        super("Applicant not found");
    }
}
