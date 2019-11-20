package edu.netcracker.jobdealer.exceptions;

public class NoPermissionException extends ApiException {
    public NoPermissionException(String s) {
        super(s);
    }

    public NoPermissionException() {
        super("You have no permission to do this");
    }
}
