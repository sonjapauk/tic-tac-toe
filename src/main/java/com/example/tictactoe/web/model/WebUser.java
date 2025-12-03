package com.example.tictactoe.web.model;

import java.util.List;

public class WebUser {
    private String id;

    private String login;

    private List<String> roles;

    public WebUser() {
    }

    public WebUser(String id, String login, List<String> roles) {
        if (id == null || id.isBlank() || login == null || login.isBlank() || roles == null) {
            throw new IllegalArgumentException("Invalid user params");
        }

        this.id = id;
        this.login = login;
        this.roles = roles;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Invalid user login");
        }

        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public List<String> getRoles() {
        return List.copyOf(roles);
    }

    public void setRoles(List<String> roles) {
        if (roles == null) {
            throw new IllegalArgumentException("Invalid roles list");
        }

        this.roles = roles;
    }
}
