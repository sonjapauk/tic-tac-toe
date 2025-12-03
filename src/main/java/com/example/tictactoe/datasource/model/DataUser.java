package com.example.tictactoe.datasource.model;

import com.example.tictactoe.datasource.converter.ListJsonConverter;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class DataUser {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Convert(converter = ListJsonConverter.class)
    @Column(nullable = false)
    private List<String> roles;

    public DataUser() {
    }

    public DataUser(String id, String login, String password, List<String> roles) {
        if (id == null || id.isBlank() || login == null || login.isBlank() || password == null || password.isBlank() || roles == null) {
            throw new IllegalArgumentException("User params are not valid!");
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
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
