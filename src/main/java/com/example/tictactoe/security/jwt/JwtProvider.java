package com.example.tictactoe.security.jwt;

import com.example.tictactoe.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtProvider {
    private final String secret;

    private final long expirationAccess;

    private final long expirationRefresh;

    public JwtProvider(String secret, long expirationAccess, long expirationRefresh) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("Jwt secret is not valid!");
        }

        this.secret = secret;
        this.expirationAccess = expirationAccess;
        this.expirationRefresh = expirationRefresh;
    }

    private SecretKey getKey() {
        byte[] decoded = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decoded);
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", user.getId());
        claims.put("roles", user.getRoles().stream().map(Enum::name).toList());

        return generateToken(user.getLogin(), claims, expirationAccess);
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", user.getId());

        return generateToken(user.getLogin(), claims, expirationRefresh);
    }

    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public void validateAccessToken(String accessToken) throws JwtException {
        validateToken(accessToken);
    }

    public void validateRefreshToken(String refreshToken) throws JwtException {
        validateToken(refreshToken);
    }

    private void validateToken(String token) throws JwtException {
        Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
    }

    public Jws<Claims> getClaims(String token) throws JwtException {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
    }
}
