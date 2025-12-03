package com.example.tictactoe.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class WebMatch {
    private String id;

    private WebGame game;

    private String idX;

    private String idO;

    private String state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate createdAt;

    public WebMatch(String id, WebGame game, String idX, String idO, String state, LocalDate createdAt) {
        if (id == null || id.isBlank() || game == null || state == null || state.isBlank()) {
            throw new IllegalArgumentException("Invalid match params!");
        }

        this.id = id;
        this.game = game;
        this.idX = idX;
        this.idO = idO;
        this.state = state;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getIdO() {
        return idO;
    }

    public String getIdX() {
        return idX;
    }

    public String getState() {
        return state;
    }

    public WebGame getGame() {
        return game;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Invalid match ID");
        }

        this.id = id;
    }

    public void setGame(WebGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Invalid game");
        }

        this.game = game;
    }

    public void setState(String state) {
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("Invalid state");
        }

        this.state = state;
    }

    public void setIdO(String idO) {
        this.idO = idO;
    }

    public void setIdX(String idX) {
        this.idX = idX;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty
    public String getStateMessage() {
        return switch (state) {
            case "WAITING_FOR_PLAYERS" -> "Ожидание игроков";
            case "PLAYER_X_MOVE" -> "Ход игрока с UUID: " + idX;
            case "PLAYER_O_MOVE" -> "Ход игрока с UUID: " + idO;
            case "WIN_PLAYER_X" -> "Победа игрока с UUID: " + (idX == null ? "BOT" : idX);
            case "WIN_PLAYER_O" -> "Победа игрока с UUID: " + (idO == null ? "BOT" : idO);
            case "DRAW" -> "Ничья";
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
    }
}
