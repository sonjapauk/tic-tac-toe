package com.example.tictactoe.web.model;

public class WebUserRating {
    private String userId;

    private float rate;

    public WebUserRating(String userId, float rate) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("Invalid rating params");
        }

        this.userId = userId;
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public String getUserId() {
        return userId;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("Invalid rating params");
        }

        this.userId = userId;
    }
}
