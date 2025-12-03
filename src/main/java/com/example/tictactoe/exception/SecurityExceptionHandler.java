package com.example.tictactoe.exception;

import com.example.tictactoe.web.controller.AuthController;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;

@ControllerAdvice(assignableTypes = {AuthController.class})
public class SecurityExceptionHandler {
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {
        if (e instanceof ExpiredJwtException) {
            return new ResponseEntity<>(new ErrorResponse(401, "Token expired"), HttpStatus.UNAUTHORIZED);
        }

        if (e.getCause() instanceof SignatureException) {
            return new ResponseEntity<>(new ErrorResponse(403, "Invalid token signature"), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(new ErrorResponse(403, "Invalid token"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
