package com.example.tictactoe.datasource.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matches")
public class DataMatch {
    @Id
    private String id;

    @Version
    private Long version;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "game_id", referencedColumnName = "currentId", nullable = true)
    private DataGame game;

    @Column(name = "x_player")
    private String x;

    @Column(name = "o_player")
    private String o;

    @Column(nullable = false)
    private String state;

    @Column(name = "created_at")
    private LocalDate createdAt;

    protected DataMatch() {
    }

    public DataMatch(String id, DataGame game, String x, String o, String state, LocalDate createdAt) {
        if (id == null || id.isBlank() || game == null || state == null || state.isBlank()) {
            throw new IllegalArgumentException("Invalid match params!");
        }

        this.id = id;
        this.game = game;
        this.x = x;
        this.o = o;
        this.state = state;
        this.createdAt = createdAt;
    }

    public DataGame getGame() {
        return game;
    }

    public String getState() {
        return state;
    }

    public String getO() {
        return o;
    }

    public String getX() {
        return x;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setState(String state) {
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("Invalid state");
        }

        this.state = state;
    }

    public void setGame(DataGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Invalid game!");
        }

        this.game = game;
    }

    public void setO(String o) {
        this.o = o;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Invalid match id!");
        }

        this.id = id;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
