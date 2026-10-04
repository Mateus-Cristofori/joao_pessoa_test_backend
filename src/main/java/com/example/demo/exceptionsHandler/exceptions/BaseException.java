package com.example.demo.exceptionsHandler.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus status;
    private final Map<String, String> fields;

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.fields = Map.of();
    }

    public BaseException(String message, HttpStatus status, Map<String, String> fields) {
        super(message);
        this.status = status;
        this.fields = fields;
    }
}
