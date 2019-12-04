package edu.netcracker.jobdealer.exceptions;

import java.util.UUID;

public class ResponseByVacancyIdNotFoundException extends AccountNotFoundException {
    public ResponseByVacancyIdNotFoundException(UUID id) {
        super("Response to vacancy with id: " + id.toString() + " not found");
    }
}
