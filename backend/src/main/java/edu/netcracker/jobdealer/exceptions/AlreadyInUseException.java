package edu.netcracker.jobdealer.exceptions;

public class AlreadyInUseException extends ApiException {

    public AlreadyInUseException(String msg) {
        super(msg);
    }
}
