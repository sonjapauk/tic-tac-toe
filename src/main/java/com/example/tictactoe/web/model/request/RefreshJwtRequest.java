package com.example.tictactoe.web.model.request;

public record RefreshJwtRequest(String refreshToken) {
    public RefreshJwtRequest {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Invalid refresh jwt request!");
        }
    }
}
