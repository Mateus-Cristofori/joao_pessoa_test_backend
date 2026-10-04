package com.example.demo.features.exceptions;

import com.example.demo.exceptionsHandler.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
