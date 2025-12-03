package com.example.tictactoe.web.model.response;

public record JwtResponse(String accessToken, String refreshToken) {
    public JwtResponse {
        if (accessToken == null || accessToken.isBlank() || refreshToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("Invalid jwt request");
        }
    }
}
