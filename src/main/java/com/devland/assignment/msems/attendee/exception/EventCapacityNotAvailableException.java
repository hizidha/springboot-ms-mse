package com.devland.assignment.msems.attendee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EventCapacityNotAvailableException extends RuntimeException {
    public EventCapacityNotAvailableException(String message) {
        super(message);
    }
}