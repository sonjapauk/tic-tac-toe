package com.example.tictactoe.domain.model.game;

import com.example.tictactoe.domain.model.Player;
import com.example.tictactoe.domain.model.field.Field;
import com.example.tictactoe.domain.model.field.FieldState;
import com.example.tictactoe.exception.game.GameIsAlreadyOverException;
import com.example.tictactoe.exception.game.InvalidMoveException;
import com.example.tictactoe.exception.game.InvalidPlayerException;
import com.example.tictactoe.exception.game.NoUserMoveException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Game {
    private Field field;
    private UUID currentID;

    public Game() {
        field = new Field();
        currentID = UUID.randomUUID();
    }

    public Game(Field field, UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid Game ID!");
        }

        if (field == null) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = field;
        currentID = id;
    }

    public static GameState toGameState(FieldState fieldState) {
        return switch (fieldState) {
            case IS_FULL -> GameState.DRAW;
            case IN_PROGRESS -> GameState.PLAYING;
            case FILLED_BY_X -> GameState.X_WINS;
            case FILLED_BY_O -> GameState.O_WINS;
        };
    }

    public List<Game> getAllSteps(Player player) {
        if (player != Player.X && player != Player.O) {
            throw new InvalidPlayerException("Invalid player!");
        }

        List<Field> nextFields = field.getNextFields(player.getCode());

        return nextFields.stream().map(f -> new Game(f, currentID)).toList();
    }

    public Field getField() {
        return field;
    }

    public UUID getCurrentID() {
        return currentID;
    }

    public void setField(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = field;
    }

    public void setCurrentID(UUID currentID) {
        if (currentID == null) {
            throw new IllegalArgumentException("Invalid Game ID!");
        }

        this.currentID = currentID;
    }

    public void validate(Game other, Player player) {
        if (other == null || player == null) {
            throw new IllegalArgumentException("Other game or player is null!");
        }

        validatePlayers();
        validateField(other, player);
    }

    private void validatePlayers() {
        if (field.hasExtraSymbols(Arrays.stream(Player.values()).map(Player::getCode).toList())) {
            throw new InvalidPlayerException("Invalid player!");
        }
    }

    private void validateField(Game other, Player player) {
        if (other.getState() == GameState.PLAYING) {
            if (field.equals(other.field)) {
                throw new NoUserMoveException("Player did not move!");
            }

            if (!field.hasValidStep(other.field, player.getCode())) {
                throw new InvalidMoveException("Illegal move!");
            }
        } else if (!field.equals(other.field)) {
            throw new GameIsAlreadyOverException("Game is already over");
        }
    }

    public Player getPlayer() {
        return Player.fromInt(field.getLessSymbol());
    }

    public GameState getState() {
        return toGameState(field.getFieldState());
    }
}
