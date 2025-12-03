package com.example.tictactoe.domain.model;

import java.util.List;
import java.util.UUID;

public class User {
    private UUID id;
    private String login;
    private String password;

    private List<Role> roles;

    public User(String login, String password, List<Role> roles) {
        this(UUID.randomUUID(), login, password, roles);
    }

    public User(UUID id, String login, String password, List<Role> roles) {
        if (id == null || login == null || login.isBlank() || password == null || password.isBlank() || roles == null) {
            throw new IllegalArgumentException("User params is not valid!");
        }

        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is not valid!");
        }

        this.id = id;
    }

    public void setLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login is not valid!");
        }

        this.login = login;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is not valid!");
        }

        this.password = password;
    }

    public List<Role> getRoles() {
        return List.copyOf(roles);
    }

    public void setRoles(List<Role> roles) {
        if (roles == null) {
            throw new IllegalArgumentException("Invalid roles list");
        }

        this.roles = roles;
    }
}
