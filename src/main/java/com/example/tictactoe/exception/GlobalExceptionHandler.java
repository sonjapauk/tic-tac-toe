package com.example.tictactoe.exception;

import com.example.tictactoe.exception.game.GameException;
import com.example.tictactoe.exception.game.GameIsAlreadyOverException;
import com.example.tictactoe.exception.game.GameNotFoundException;
import com.example.tictactoe.exception.game.InvalidMoveException;
import com.example.tictactoe.exception.match.MatchException;
import com.example.tictactoe.exception.user.UserException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({GameIsAlreadyOverException.class, InvalidMoveException.class})
    public ResponseEntity<ErrorResponse> handleGameConflict(RuntimeException e) {
        return new ResponseEntity<>(new ErrorResponse(409, e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGameNotFound(GameNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameException.class)
    public ResponseEntity<ErrorResponse> handleGameBadRequest(GameException e) {
        return new ResponseEntity<>(new ErrorResponse(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataException(DataIntegrityViolationException e) {
        String message = "Database error";

        if (e.getMostSpecificCause().getMessage().contains("users_login_key")) {
            message = "Login already taken";
        }

        return new ResponseEntity<>(new ErrorResponse(400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        return new ResponseEntity<>(new ErrorResponse(409, e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MatchException.class)
    public ResponseEntity<ErrorResponse> handleMatchException(MatchException e) {
        return new ResponseEntity<>(new ErrorResponse(409, e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
