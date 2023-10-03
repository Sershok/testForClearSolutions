package com.example.testforclearsolutions.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserYoungException extends RuntimeException {

    public UserYoungException(String message) {
        super(message);
    }
}
