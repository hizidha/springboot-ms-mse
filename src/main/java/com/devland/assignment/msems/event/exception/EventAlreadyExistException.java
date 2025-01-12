package com.devland.assignment.msems.event.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EventAlreadyExistException extends RuntimeException {
    public EventAlreadyExistException(String message) {
        super(message);
    }
}