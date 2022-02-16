package ru.javawebinar.restaurant_voting_system.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Menu is empty")  // 424
public class EmptyMenuException extends RuntimeException {
    public EmptyMenuException(String message) {
        super(message);
    }
}