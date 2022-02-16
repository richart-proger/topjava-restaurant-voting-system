package ru.javawebinar.restaurant_voting_system.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Voting time is expired")  // 405
public class TimeExpiredException extends RuntimeException {
    public TimeExpiredException(String message) {
        super(message);
    }
}