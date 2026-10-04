package com.example.demo.exceptionsHandler;

import com.example.demo.exceptionsHandler.exceptions.BaseException;
import com.example.demo.exceptionsHandler.model.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> defaultHandlerException(
        Exception exception,
        HttpStatus httpStatus,
        HttpServletRequest request,
        Map<String, String> fields
    ) {
        ErrorResponse error = new ErrorResponse(
            exception.getMessage(),
            httpStatus.value(),
            request.getRequestURI(),
            fields,
            Instant.now()
        );
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handlerBaseException(
        BaseException exception,
        HttpServletRequest request
    ) {
        return defaultHandlerException(
            exception,
            exception.getStatus(),
            request,
            exception.getFields()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
        EntityNotFoundException exception,
        HttpServletRequest request
    ) {
        return defaultHandlerException(
            exception,
            HttpStatus.NOT_FOUND,
            request,
            Collections.emptyMap()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse error = new ErrorResponse(
            "Validation failed for argument",
            HttpStatus.BAD_REQUEST.value(),
            request.getRequestURI(),
            fieldErrors,
            Instant.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerGenericException(
        Exception exception,
        HttpServletRequest request
    ) {
        return defaultHandlerException(
            exception,
            HttpStatus.INTERNAL_SERVER_ERROR,
            request,
            Collections.emptyMap()
        );
    }
}
