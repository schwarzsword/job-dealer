package edu.netcracker.jobdealer.exceptions;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException() {
        super("Task not found");
    }
}
