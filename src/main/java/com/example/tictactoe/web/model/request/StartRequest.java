package com.example.tictactoe.web.model.request;

public record StartRequest(boolean vsComputer, String chosenMark) {
    public StartRequest {
        if (chosenMark == null || chosenMark.isBlank()) {
            throw new IllegalArgumentException("Invalid start up request");
        }
    }
}
