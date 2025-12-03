package com.example.tictactoe.exception.game;

public class GameIsAlreadyOverException extends GameException {
    public GameIsAlreadyOverException(String message) {
        super(message);
    }
}
