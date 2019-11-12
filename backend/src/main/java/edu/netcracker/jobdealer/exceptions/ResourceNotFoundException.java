package edu.netcracker.jobdealer.exceptions;

public class ResourceNotFoundException extends NotFoundException {

    public ResourceNotFoundException() {
        super("Resource not found");
    }
}
