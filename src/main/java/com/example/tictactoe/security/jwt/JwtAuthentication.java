package com.example.tictactoe.security.jwt;

import com.example.tictactoe.domain.model.Role;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public class JwtAuthentication implements Authentication {
    private final UUID principal;

    private final List<Role> roles;

    private boolean authenticated;

    public JwtAuthentication(UUID principal, List<Role> roles) {
        if (principal == null || roles == null) {
            throw new IllegalArgumentException("Invalid authentication params");
        }

        this.principal = principal;
        this.roles = roles;
    }

    @Override
    public List<Role> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public UUID getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal.toString();
    }
}
