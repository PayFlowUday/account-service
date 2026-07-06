package com.payflow.account.exception.handler;

import com.payflow.account.exception.EmailAlreadyExistsException;
import com.payflow.account.exception.InsufficientBalanceException;
import com.payflow.account.exception.UserNotFoundException;
import com.payflow.account.exception.InvalidCredentialsException;
import com.payflow.account.exception.WalletInactiveException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>(); // LinkedHashMap preserves insertion order for JSON
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return createErrorResponse(HttpStatus.BAD_REQUEST, message);
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        ex.printStackTrace(); // ← add this temporarily
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()); // ← show actual message
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> insufficientBalanceException(InsufficientBalanceException insufficientBalanceException){
        return createErrorResponse(HttpStatus.BAD_REQUEST,insufficientBalanceException.getMessage());
    }

    @ExceptionHandler(WalletInactiveException.class)
    public ResponseEntity<Map<String, Object>> handleWalletInactive(WalletInactiveException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


}
