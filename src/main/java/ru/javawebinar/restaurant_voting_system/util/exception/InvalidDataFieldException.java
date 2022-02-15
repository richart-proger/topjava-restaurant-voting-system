package ru.javawebinar.restaurant_voting_system.util.exception;

public class InvalidDataFieldException extends RuntimeException {
    public InvalidDataFieldException(String message) {
        super(message);
    }
}