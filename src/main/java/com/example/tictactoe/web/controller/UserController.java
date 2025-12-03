package com.example.tictactoe.web.controller;

import com.example.tictactoe.domain.service.UserService;
import com.example.tictactoe.web.mapper.Mapper;
import com.example.tictactoe.security.CurrentUser;
import com.example.tictactoe.web.model.WebUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebUser> getUser(@PathVariable String id) {
        return ResponseEntity.ok(Mapper.toWeb(service.getUser(UUID.fromString(id))));
    }

    @GetMapping("/me")
    public ResponseEntity<WebUser> getUser() {
        return ResponseEntity.ok(Mapper.toWeb(service.getUser(CurrentUser.getId())));
    }
}
