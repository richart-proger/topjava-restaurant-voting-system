package ru.javawebinar.restaurant_voting_system.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid data binding")  // 422
public class InvalidDataFieldException extends RuntimeException {
    public InvalidDataFieldException(String message) {
        super(message);
    }
}