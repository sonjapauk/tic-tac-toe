package com.example.tictactoe.web.model.request;

public record JwtRequest(String login, String password) {
    public JwtRequest {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Invalid jwt request");
        }
    }
}
