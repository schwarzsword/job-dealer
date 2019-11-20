package edu.netcracker.jobdealer.exceptions;

public class ResumeAlreadyExistsException extends ExistsException {
    public ResumeAlreadyExistsException() {
        super("Resume already exists");
    }
}
