package com.example.tictactoe.security;

import com.example.tictactoe.security.service.AuthService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

public class AuthFilter extends GenericFilterBean {
    private final List<String> permitted;
    private final AuthService authService;

    public AuthFilter(AuthService authService, List<String> permitted) {
        if (authService == null || permitted == null) {
            throw new IllegalArgumentException("Invalid filter params!");
        }

        this.authService = authService;
        this.permitted = permitted;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        if (permitted.stream().anyMatch(path::equals)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String authHeader = httpRequest.getHeader("Authorization");

            Authentication auth = authService.getAuthentication(authHeader);
            auth.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    public List<String> getPermitted() {
        return permitted;
    }
}
