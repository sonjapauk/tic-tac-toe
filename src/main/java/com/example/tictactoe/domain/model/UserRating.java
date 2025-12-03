package com.example.tictactoe.domain.model;

import java.util.UUID;

public class UserRating {
    private UUID userId;

    private float rate;

    public UserRating(UUID userId, float rate) {
        if (userId == null) {
            throw new IllegalArgumentException("Invalid rating params");
        }

        this.userId = userId;
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Invalid rating params");
        }

        this.userId = userId;
    }
}
