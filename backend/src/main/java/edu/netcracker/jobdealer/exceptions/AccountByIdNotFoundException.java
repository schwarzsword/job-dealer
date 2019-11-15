package edu.netcracker.jobdealer.exceptions;

import java.util.UUID;

public class AccountByIdNotFoundException extends AccountNotFoundException {
    public AccountByIdNotFoundException(UUID id){
        super("Account with id: "+id.toString()+" not found");
    }
}
