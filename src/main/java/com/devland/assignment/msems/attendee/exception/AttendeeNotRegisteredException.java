package com.devland.assignment.msems.attendee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AttendeeNotRegisteredException extends RuntimeException {
    public AttendeeNotRegisteredException(String message) {
        super(message);
    }
}