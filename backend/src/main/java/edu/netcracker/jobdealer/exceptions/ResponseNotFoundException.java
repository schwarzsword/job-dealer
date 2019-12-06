package edu.netcracker.jobdealer.exceptions;

public class ResponseNotFoundException extends AccountNotFoundException {
    public ResponseNotFoundException() {
        super("Response not found");
    }
}
