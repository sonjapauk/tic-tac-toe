package com.example.tictactoe.security;

import com.example.tictactoe.exception.user.UserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class CurrentUser {
    public static UUID getId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new UserException("User is not authenticated");
        }

        return (UUID) auth.getPrincipal();
    }
}
