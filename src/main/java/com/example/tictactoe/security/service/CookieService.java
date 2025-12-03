package com.example.tictactoe.security.service;

import org.springframework.http.ResponseCookie;

public interface CookieService {
    ResponseCookie create(String path, String name, String value);

    ResponseCookie clear(String path, String name);
}
