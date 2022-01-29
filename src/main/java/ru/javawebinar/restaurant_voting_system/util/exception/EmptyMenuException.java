package ru.javawebinar.restaurant_voting_system.util.exception;

public class EmptyMenuException extends RuntimeException {
    public EmptyMenuException(String message) {
        super(message);
    }
}