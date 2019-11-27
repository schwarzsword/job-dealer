package edu.netcracker.jobdealer.exceptions;

public class ResumeNotFoundException extends NotFoundException {
    public ResumeNotFoundException() {
        super("Resource not found");
    }
}
