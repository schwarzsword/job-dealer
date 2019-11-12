package edu.netcracker.jobdealer.exceptions;

public class DoubleVotingException extends ApiException {
    public DoubleVotingException() {
        super("You can't vote twice");
    }
}
