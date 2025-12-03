package com.example.tictactoe.domain.model.match;

import com.example.tictactoe.domain.model.Player;
import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.game.GameState;

import java.time.LocalDate;
import java.util.UUID;

public class Match {
    private UUID id;
    private Game game;

    private UUID x;

    private UUID o;

    private MatchState state;

    private final LocalDate createdAt;

    public Match(Game game, UUID x, UUID o, MatchState state) {
        this(UUID.randomUUID(), game, x, o, state, LocalDate.now());
    }

    public Match(UUID id, Game game, UUID x, UUID o, MatchState state, LocalDate createdAt) {
        if (id == null || game == null || state == null) {
            throw new IllegalArgumentException("Invalid match params!");
        }

        this.id = id;
        this.game = game;
        this.x = x;
        this.o = o;
        this.state = state;
        this.createdAt = createdAt;
    }

    public Game getGame() {
        return game;
    }

    public MatchState getState() {
        return state;
    }

    public UUID getO() {
        return o;
    }

    public UUID getX() {
        return x;
    }

    public void setGame(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Invalid game!");
        }

        this.game = game;
        updateState();
    }

    public void setO(UUID o) {
        this.o = o;
    }

    public void setX(UUID x) {
        this.x = x;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid match ID!");
        }

        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public boolean isWaitingForPlayers() {
        return state == MatchState.WAITING_FOR_PLAYERS && (!hasX() || !hasO());
    }

    public boolean hasX() {
        return x != null;
    }

    public boolean hasO() {
        return o != null;
    }

    public void addUser(UUID user) {
        if (!hasX()) {
            x = user;
        } else if (!hasO()) {
            o = user;
        }

        state = hasX() && hasO() ? MatchState.PLAYER_X_MOVE : MatchState.WAITING_FOR_PLAYERS;
    }

    public boolean isUserTurn(UUID userId) {
        return userId != null &&
                (state == MatchState.PLAYER_X_MOVE && userId.equals(x)
                        || state == MatchState.PLAYER_O_MOVE && userId.equals(o));
    }

    public boolean vsBot() {
        return state != MatchState.WAITING_FOR_PLAYERS && (hasO() ^ hasX());
    }

    public void updateState() {
        GameState gameState = game.getState();

        state = switch (gameState) {
            case X_WINS -> MatchState.WIN_PLAYER_X;
            case O_WINS -> MatchState.WIN_PLAYER_O;
            case DRAW -> MatchState.DRAW;
            case PLAYING -> game.getPlayer() == Player.X ?
                    MatchState.PLAYER_X_MOVE : MatchState.PLAYER_O_MOVE;
            default -> state;
        };
    }

    public boolean isOver() {
        return state != MatchState.WAITING_FOR_PLAYERS
                && state != MatchState.PLAYER_X_MOVE
                && state != MatchState.PLAYER_O_MOVE;
    }
}
