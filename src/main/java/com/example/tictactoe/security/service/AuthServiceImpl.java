package com.example.tictactoe.security.service;

import com.example.tictactoe.domain.model.Role; //TODO: вынести
import com.example.tictactoe.domain.model.User;
import com.example.tictactoe.domain.service.UserService;
import com.example.tictactoe.exception.user.UserException;
import com.example.tictactoe.security.jwt.*;
import com.example.tictactoe.web.model.request.JwtRequest;
import com.example.tictactoe.web.model.request.RefreshJwtRequest;
import com.example.tictactoe.web.model.request.SignUpRequest;
import com.example.tictactoe.web.model.response.JwtResponse;
import org.springframework.security.core.Authentication;

import java.util.*;

public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserService userService, JwtProvider jwtProvider) {
        if (userService == null || jwtProvider == null) {
            throw new IllegalArgumentException("Invalid user service or jwt provider!");
        }

        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void register(SignUpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Invalid request");
        }

        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);

        userService.createUser(request.login(), request.password(), roles);
    }

    @Override
    public JwtResponse authorize(JwtRequest request) {
        UUID userId = userService.getUserId(request.login(), request.password());
        User user = userService.getUser(userId);

        return new JwtResponse(jwtProvider.generateAccessToken(user), jwtProvider.generateRefreshToken(user));
    }

    @Override
    public JwtResponse refreshAccessToken(RefreshJwtRequest request) {
        jwtProvider.validateRefreshToken(request.refreshToken());

        UUID userId = UUID.fromString(jwtProvider.getClaims(request.refreshToken()).getPayload().get("uuid", String.class));

        return new JwtResponse(jwtProvider.generateAccessToken(userService.getUser(userId)), request.refreshToken());
    }

    @Override
    public JwtResponse refreshRefreshToken(RefreshJwtRequest request) {
        jwtProvider.validateRefreshToken(request.refreshToken());

        UUID userId = UUID.fromString(jwtProvider.getClaims(request.refreshToken()).getPayload().get("uuid", String.class));
        User user = userService.getUser(userId);

        return new JwtResponse(jwtProvider.generateAccessToken(user), jwtProvider.generateRefreshToken(user));
    }

    @Override
    public Authentication getAuthentication(String header) {
        String token = getHeaderPayload(header);
        jwtProvider.validateAccessToken(token);

        return JwtUtil.create(jwtProvider.getClaims(token));
    }

    private String getHeaderPayload(String header) {
        if (!header.startsWith("Bearer ")) {
            throw new UserException("Invalid authorization data");
        }

        return header.substring("Bearer ".length());
    }
}
