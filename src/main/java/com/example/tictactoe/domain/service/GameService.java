package com.example.tictactoe.domain.service;

import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.Player;

public interface GameService {
    Game getStep(Game userGame);

    void validateGame(Game userGame, Game savedGame, Player player);

    boolean isOver(Game userGame);

    Game createGame();
}
