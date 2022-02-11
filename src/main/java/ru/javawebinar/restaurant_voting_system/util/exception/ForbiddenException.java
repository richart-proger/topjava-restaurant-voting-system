package ru.javawebinar.restaurant_voting_system.util.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
