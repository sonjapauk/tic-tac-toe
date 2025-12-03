package com.example.tictactoe.domain.model.strategy;

import com.example.tictactoe.domain.model.Player;
import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.game.GameState;

import java.util.ArrayList;
import java.util.List;

public class Minimax implements GameStrategy {
    record MinimaxResult(int score, Game step) {
    }

    private MinimaxResult minimax(Game game, Player player) {
        GameState state = game.getState();

        if (state != GameState.PLAYING) {
            return new MinimaxResult(state == GameState.X_WINS && player == Player.O ? -1 :
                    state == GameState.O_WINS && player == Player.X ? 1 : 0,
                    game);
        }

        List<Game> steps = game.getAllSteps(player);

        List<MinimaxResult> result = new ArrayList<>();

        for (Game step : steps) {
            MinimaxResult candidate = minimax(step, player == Player.X ? Player.O : Player.X);
            result.add(new MinimaxResult(candidate.score(), step));
        }

        MinimaxResult max = result.get(0);
        MinimaxResult min = max;

        for (MinimaxResult res : result) {
            int resScore = res.score();

            if (resScore > max.score()) max = res;
            if (resScore < min.score()) min = res;
        }

        return player == Player.O ? max : min;
    }

    @Override
    public Game getBestMove(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Other game is null!");
        }

        return minimax(game, game.getPlayer()).step();
    }
}
