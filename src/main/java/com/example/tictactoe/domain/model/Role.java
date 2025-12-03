package com.example.tictactoe.domain.model;

import com.example.tictactoe.exception.user.UserException;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }

    public static Role fromString(String input) {
        for (Role e : values()) {
            if (e.toString().equalsIgnoreCase(input)) {
                return e;
            }
        }

        throw new UserException("No such role:" + input);
    }
}
