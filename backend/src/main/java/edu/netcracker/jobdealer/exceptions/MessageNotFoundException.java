package edu.netcracker.jobdealer.exceptions;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(String message_not_found) {
        super(message_not_found);
    }
}
