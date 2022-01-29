package ru.javawebinar.restaurant_voting_system.util.exception;

public class TimeExpiredException extends RuntimeException {
    public TimeExpiredException(String message) {
        super(message);
    }
}