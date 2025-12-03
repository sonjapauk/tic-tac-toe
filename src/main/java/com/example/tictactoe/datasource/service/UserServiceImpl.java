package com.example.tictactoe.datasource.service;

import com.example.tictactoe.datasource.mapper.Mapper;
import com.example.tictactoe.datasource.model.DataUser;
import com.example.tictactoe.datasource.repository.UserRepository;
import com.example.tictactoe.domain.model.Role;
import com.example.tictactoe.domain.model.User;
import com.example.tictactoe.domain.service.UserService;
import com.example.tictactoe.exception.user.UserException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        if (userRepository == null || passwordEncoder == null) {
            throw new IllegalArgumentException("Invalid repository");
        }

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(String login, String password, List<Role> roles) {
        if (login == null || login.isBlank() || password == null || password.isBlank() || roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("User params are not valid!");
        }

        Optional<DataUser> inRepo = userRepository.findByLogin(login);

        if (inRepo.isPresent()) {
            throw new UserException("Login is already occupied!");
        }

        User user = new User(login, passwordEncoder.encode(password), roles);
        userRepository.save(Mapper.toDataSource(user));
    }

    @Override
    public User getUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid ID");
        }

        return Mapper.toDomain(userRepository.findById(id.toString()).orElseThrow(() -> new UserException("User is not found")));
    }


    @Override
    public UUID getUserId(String login, String password) {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("User params are not valid!");
        }

        DataUser inRepo = userRepository.findByLogin(login).orElseThrow(() -> new UserException("User is not found!"));

        if (!passwordEncoder.matches(password, inRepo.getPassword())) {
            throw new UserException("Passwords don't match");
        }

        return UUID.fromString(inRepo.getId());
    }
}
