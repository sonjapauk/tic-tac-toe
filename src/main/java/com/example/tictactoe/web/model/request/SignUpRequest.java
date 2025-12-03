package com.example.tictactoe.web.model.request;

public record SignUpRequest(String login, String password) {
    public SignUpRequest {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Invalid sign up request");
        }
    }
}
