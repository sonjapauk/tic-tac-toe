package com.example.tictactoe.domain.service;

import com.example.tictactoe.domain.model.UserRating;
import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.match.Match;
import com.example.tictactoe.domain.model.Player;

import java.util.List;
import java.util.UUID;

public interface MatchService {
    Match createMatch(UUID userId, boolean vsComputer, Player chosenMark);

    void join(UUID matchId, UUID userId);

    List<UUID> getAvailableGames();

    Match makeMove(UUID matchId);

    boolean isOver(Match match);

    void validateMove(Game userGame, UUID userId, UUID matchId);

    UUID getGameId(UUID matchId);

    Match getMatchById(UUID matchId);

    void save(Match match);

    Match applyMove(UUID matchId, Game userMove);

    boolean vsBot(Match match);

    List<UUID> getFinishedGames(UUID userId);

    List<UserRating> getBestPlayers(int N);
}
