package edu.netcracker.jobdealer.exceptions;

public class VacancyNotFoundException extends RuntimeException {
    public VacancyNotFoundException(String msg) {
        super(msg);
    }
}
