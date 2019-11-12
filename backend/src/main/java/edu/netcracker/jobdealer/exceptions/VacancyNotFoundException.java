package edu.netcracker.jobdealer.exceptions;

public class VacancyNotFoundException extends NotFoundException {
    public VacancyNotFoundException(String vacancyName) {
        super("Vacancy " + vacancyName + " not found");
    }

    public VacancyNotFoundException() {
        super("Vacancy not found");
    }
}
