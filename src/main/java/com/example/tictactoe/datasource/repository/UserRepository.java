package com.example.tictactoe.datasource.repository;

import com.example.tictactoe.datasource.model.DataUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<DataUser, String> {
    Optional<DataUser> findByLogin(String login);
}
