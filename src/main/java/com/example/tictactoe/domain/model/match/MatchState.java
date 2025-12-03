package com.example.tictactoe.domain.model.match;

public enum MatchState {
    WAITING_FOR_PLAYERS, PLAYER_X_MOVE, PLAYER_O_MOVE, WIN_PLAYER_X, WIN_PLAYER_O, DRAW;

    public static MatchState fromString(String input) {
        for (MatchState e : values()) {
            if (e.name().equalsIgnoreCase(input)) {
                return e;
            }
        }

        throw new IllegalArgumentException("No such state:" + input);
    }
}
