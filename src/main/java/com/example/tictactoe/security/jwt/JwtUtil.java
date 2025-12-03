package com.example.tictactoe.security.jwt;

import com.example.tictactoe.domain.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;
import java.util.UUID;

public class JwtUtil {
    public static JwtAuthentication create(Jws<Claims> claimsJws) {
        Claims claims = claimsJws.getPayload();

        UUID principal = UUID.fromString(claims.get("uuid", String.class));
        List<?> rawRoles = claims.get("roles", List.class);
        List<Role> roles = rawRoles.stream().map(raw -> Role.fromString(raw.toString())).toList();

        return new JwtAuthentication(principal, roles);
    }
}
