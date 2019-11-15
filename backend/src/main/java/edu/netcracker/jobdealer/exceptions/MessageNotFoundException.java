package edu.netcracker.jobdealer.exceptions;

public class MessageNotFoundException extends NotFoundException {
    public MessageNotFoundException() {
        super("Message not found");
    }
}
