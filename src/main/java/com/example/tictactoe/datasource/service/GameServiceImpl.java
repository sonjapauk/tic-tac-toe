package com.example.tictactoe.datasource.service;

import com.example.tictactoe.domain.model.*;
import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.game.GameState;
import com.example.tictactoe.domain.model.strategy.GameStrategy;
import com.example.tictactoe.domain.model.strategy.Minimax;
import com.example.tictactoe.domain.service.GameService;
import com.example.tictactoe.exception.game.InvalidGameException;
import org.springframework.beans.factory.annotation.Autowired;

public class GameServiceImpl implements GameService {
    private final GameStrategy strategy;

    @Autowired
    public GameServiceImpl() {
        strategy = new Minimax();
    }

    @Override
    public Game getStep(Game userGame) {
        if (userGame == null) {
            throw new InvalidGameException("Game is null");
        }

        return strategy.getBestMove(userGame);
    }

    @Override
    public void validateGame(Game userGame, Game savedGame, Player player) {
        if (userGame == null || savedGame == null) {
            throw new InvalidGameException("Game is null");
        }

        userGame.validate(savedGame, player);
    }

    @Override
    public boolean isOver(Game userGame) {
        if (userGame == null) {
            throw new InvalidGameException("Game is null");
        }

        return userGame.getState() != GameState.PLAYING;
    }

    public Game createGame() {
        return new Game();
    }
}
