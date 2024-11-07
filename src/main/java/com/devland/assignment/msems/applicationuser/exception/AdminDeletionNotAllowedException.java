package com.devland.assignment.msems.applicationuser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AdminDeletionNotAllowedException extends RuntimeException {
    public AdminDeletionNotAllowedException(String message) {
        super(message);
    }
}