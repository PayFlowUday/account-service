package com.payflow.account.exception.handler;

import com.payflow.account.exception.EmailAlreadyExistsException;
import com.payflow.account.exception.UserNotFoundException;
import com.payflow.account.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>(); // LinkedHashMap preserves insertion order for JSON
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> emailAlreadyExistException(EmailAlreadyExistsException emailAlreadyExistsException){
        return createErrorResponse(HttpStatus.CONFLICT,emailAlreadyExistsException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> userNotFoundException(UserNotFoundException userNotFoundException){
        return createErrorResponse(HttpStatus.NOT_FOUND,userNotFoundException.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> invalidCredentialsException(
            InvalidCredentialsException ex) {

        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


}
