package com.example.tictactoe.exception.game;

public class InvalidMoveException extends GameException {
    public InvalidMoveException(String message) {
        super(message);
    }
}
