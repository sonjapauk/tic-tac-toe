package com.example.tictactoe.security.service;

import com.example.tictactoe.web.model.request.SignUpRequest;
import com.example.tictactoe.web.model.request.JwtRequest;
import com.example.tictactoe.web.model.response.JwtResponse;
import com.example.tictactoe.web.model.request.RefreshJwtRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void register(SignUpRequest request);

    JwtResponse authorize(JwtRequest request);

    JwtResponse refreshAccessToken(RefreshJwtRequest request);

    JwtResponse refreshRefreshToken(RefreshJwtRequest request);

    Authentication getAuthentication(String header);
}
