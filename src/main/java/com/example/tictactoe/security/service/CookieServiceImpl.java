package com.example.tictactoe.security.service;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieServiceImpl implements CookieService {
    private final int dayDuration;

    public CookieServiceImpl(int dayDuration) {
        this.dayDuration = dayDuration;
    }

    @Override
    public ResponseCookie create(String path, String name, String value) {
        if (path == null || path.isBlank() || name == null || name.isBlank() || value == null || value.isBlank()) {
            throw new IllegalArgumentException("Invalid cookie params");
        }

        return base(path, name)
                .value(value)
                .maxAge(Duration.ofDays(dayDuration))
                .build();
    }

    @Override
    public ResponseCookie clear(String path, String name) {
        if (path == null || path.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid cookie params");
        }

        return base(path, name)
                .maxAge(Duration.ofDays(0))
                .build();
    }

    private ResponseCookie.ResponseCookieBuilder base(String path, String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(false)
                .path(path)
                .sameSite("Strict");
    }
}
