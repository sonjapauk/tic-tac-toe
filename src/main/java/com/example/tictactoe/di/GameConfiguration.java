package com.example.tictactoe.di;

import com.example.tictactoe.datasource.repository.*;
import com.example.tictactoe.datasource.service.GameServiceImpl;
import com.example.tictactoe.datasource.service.MatchServiceImpl;
import com.example.tictactoe.domain.service.MatchService;
import com.example.tictactoe.domain.service.GameService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {
    @Bean
    public GameService gameService() {
        return new GameServiceImpl();
    }

    @Bean
    public MatchService matchService(MatchRepository repository, GameService gameService) {
        return new MatchServiceImpl(repository, gameService);
    }
}
