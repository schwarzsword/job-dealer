package edu.netcracker.jobdealer.exceptions;

public class ReviewNotFountException extends NotFoundException {
    public ReviewNotFountException() {
        super("Review not found");
    }
}
