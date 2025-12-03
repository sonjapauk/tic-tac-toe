package com.example.tictactoe.domain.model.strategy;

import com.example.tictactoe.domain.model.game.Game;

public interface GameStrategy {
    Game getBestMove(Game userGame);
}
