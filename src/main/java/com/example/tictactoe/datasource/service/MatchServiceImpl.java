package com.example.tictactoe.datasource.service;

import com.example.tictactoe.datasource.mapper.Mapper;
import com.example.tictactoe.datasource.model.DataMatch;
import com.example.tictactoe.datasource.repository.MatchRepository;
import com.example.tictactoe.domain.model.*;
import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.match.Match;
import com.example.tictactoe.domain.model.match.MatchState;
import com.example.tictactoe.domain.service.GameService;
import com.example.tictactoe.domain.service.MatchService;
import com.example.tictactoe.exception.match.MatchException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public class MatchServiceImpl implements MatchService {
    private final MatchRepository repository;

    private final GameService gameService;

    public MatchServiceImpl(MatchRepository repository, GameService gameService) {
        if (repository == null || gameService == null) {
            throw new IllegalArgumentException("Invalid service params!");
        }

        this.repository = repository;
        this.gameService = gameService;
    }

    @Override
    public Match createMatch(UUID userId, boolean vsComputer, Player chosenMark) {
        if (userId == null) {
            throw new MatchException("User must not be null for match creation!");
        }

        Match match = chosenMark == Player.X ?
                new Match(gameService.createGame(), userId, null, vsComputer ? MatchState.PLAYER_X_MOVE : MatchState.WAITING_FOR_PLAYERS) :
                new Match(gameService.createGame(), null, userId, vsComputer ? MatchState.PLAYER_X_MOVE : MatchState.WAITING_FOR_PLAYERS);

        if (vsComputer && chosenMark == Player.O) {
            match.setGame(gameService.getStep(match.getGame()));
        }

        repository.save(Mapper.toDataSource(match));

        return match;
    }

    @Override
    @Transactional
    public void join(UUID matchId, UUID userId) {
        if (userId == null || matchId == null) {
            throw new MatchException("Failed to join");
        }

        DataMatch dataMatch = load(matchId);
        Match match = Mapper.toDomain(dataMatch);

        if (!match.isWaitingForPlayers()) {
            throw new MatchException("Match has already started");
        }

        try {
            match.addUser(userId);
            Mapper.updateDataMatch(dataMatch, match);
            repository.save(dataMatch);
        } catch (OptimisticLockException e) {
            throw new MatchException("Match has already started");
        }
    }

    @Override
    public List<UUID> getAvailableGames() {
        return repository.findIdsByState(MatchState.WAITING_FOR_PLAYERS.toString()).stream().map(UUID::fromString).toList();
    }

    @Override
    public Match makeMove(UUID matchId) {
        if (matchId == null) {
            throw new MatchException("Failed to move. Match is null");
        }

        DataMatch dataMatch = load(matchId);
        Match match = Mapper.toDomain(dataMatch);

        match.setGame(gameService.getStep(match.getGame()));

        Mapper.updateDataMatch(dataMatch, match);
        repository.save(dataMatch);

        return match;
    }

    @Override
    public boolean isOver(Match match) {
        if (match == null) {
            throw new MatchException("Match is null");
        }

        return match.isOver();
    }

    @Override
    public void validateMove(Game userGame, UUID userId, UUID matchId) {
        if (userId == null || matchId == null || userGame == null) {
            throw new MatchException("Validation failure");
        }

        Match match = Mapper.toDomain(load(matchId));

        if (!match.isUserTurn(userId)) {
            throw new MatchException("It's not your turn!");
        }

        gameService.validateGame(userGame, match.getGame(), match.getState() == MatchState.PLAYER_X_MOVE ? Player.X : Player.O);
    }

    @Override
    public UUID getGameId(UUID matchId) {
        if (matchId == null) {
            throw new MatchException("Match ID is null");
        }

        String gameId = repository.findGameIdById(matchId.toString());

        if (gameId == null) {
            throw new MatchException("Game is not found");
        }

        return UUID.fromString(gameId);
    }

    @Override
    public Match getMatchById(UUID matchId) {
        if (matchId == null) {
            throw new MatchException("Match ID is null");
        }

        return Mapper.toDomain(load(matchId));
    }

    @Override
    public void save(Match match) {
        if (match == null) {
            throw new MatchException("Match is null");
        }

        repository.save(Mapper.toDataSource(match));
    }

    @Override
    public Match applyMove(UUID matchId, Game userMove) {
        if (matchId == null || userMove == null) {
            throw new MatchException("Failed to apply");
        }

        DataMatch loadedMatch = load(matchId);
        Match match = Mapper.toDomain(loadedMatch);

        match.setGame(userMove);

        Mapper.updateDataMatch(loadedMatch, match);
        repository.save(loadedMatch);

        return match;
    }

    @Override
    public boolean vsBot(Match match) {
        if (match == null) {
            throw new MatchException("Match is null");
        }

        return match.vsBot();
    }

    @Override
    public List<UUID> getFinishedGames(UUID userId) {
        return repository.findFinishedIdsByPlayerId(userId.toString()).stream().map(UUID::fromString).toList();
    }

    @Override
    public List<UserRating> getBestPlayers(int N) {
        return repository.findTopByWinRate(N).stream().map(Mapper::toDomain).toList();
    }

    private DataMatch load(UUID matchId) {
        return repository.findById(matchId.toString()).orElseThrow(() -> new MatchException("Match not found"));
    }
}
