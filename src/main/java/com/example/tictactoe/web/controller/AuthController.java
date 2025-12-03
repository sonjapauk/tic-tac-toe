package com.example.tictactoe.web.controller;

import com.example.tictactoe.security.service.CookieService;
import com.example.tictactoe.web.model.request.SignUpRequest;
import com.example.tictactoe.web.model.request.JwtRequest;
import com.example.tictactoe.web.model.response.JwtResponse;
import com.example.tictactoe.web.model.request.RefreshJwtRequest;
import com.example.tictactoe.security.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    private final CookieService cookieService;

    public AuthController(AuthService authService, CookieService cookieService) {
        this.authService = authService;
        this.cookieService = cookieService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> logIn(@RequestBody JwtRequest request) {
        JwtResponse response = authService.authorize(request);

        ResponseCookie refreshCookie = cookieService.create("/refresh", "refreshToken", response.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    @PostMapping("/refresh/access")
    public ResponseEntity<JwtResponse> refreshAccess(
            @CookieValue(name = "refreshToken", required = false) String cookie,
            @RequestBody(required = false) RefreshJwtRequest request) {
        if (cookie != null && !cookie.isBlank()) {
            request = new RefreshJwtRequest(cookie);
        }

        JwtResponse response = authService.refreshAccessToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh/refresh")
    public ResponseEntity<JwtResponse> refreshRefresh(
            @CookieValue(name = "refreshToken", required = false) String cookie,
            @RequestBody(required = false) RefreshJwtRequest request) {
        if (cookie != null && !cookie.isBlank()) {
            request = new RefreshJwtRequest(cookie);
        }

        JwtResponse response = authService.refreshRefreshToken(request);

        ResponseCookie refreshCookie = cookieService.create("/refresh", "refreshToken", response.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    @PostMapping("/exit")
    public ResponseEntity<Void> logOut() {
        ResponseCookie logoutCookie = cookieService.clear("/refresh", "refreshToken");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, logoutCookie.toString())
                .build();
    }
}
