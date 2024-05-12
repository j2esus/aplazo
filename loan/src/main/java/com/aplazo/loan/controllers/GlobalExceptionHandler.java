package com.aplazo.loan.controllers;

import com.aplazo.loan.exceptions.CustomErrorResponse;
import com.aplazo.loan.exceptions.ErrorStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ErrorStatusException.class)
    public ResponseEntity<Object> exception(ErrorStatusException exception) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setError(exception.getMessage());
        errors.setStatus("FAILURE");

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
