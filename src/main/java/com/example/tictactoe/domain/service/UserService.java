package com.example.tictactoe.domain.service;

import com.example.tictactoe.domain.model.Role;
import com.example.tictactoe.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(String login, String password, List<Role> roles);

    User getUser(UUID id);

    UUID getUserId(String login, String password);
}
