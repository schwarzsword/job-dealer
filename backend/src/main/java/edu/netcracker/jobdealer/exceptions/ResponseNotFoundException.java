package edu.netcracker.jobdealer.exceptions;

import java.util.UUID;

public class ResponseNotFoundException extends AccountNotFoundException {
    public ResponseNotFoundException() {
        super("Response not found");
    }
}
