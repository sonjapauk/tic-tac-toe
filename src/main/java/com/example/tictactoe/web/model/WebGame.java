package com.example.tictactoe.web.model;

public class WebGame {
    private String state;
    private WebField field;

    public WebGame() {
    }

    public WebGame(WebField field, String state) {
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("Invalid state!");
        }

        if (field == null) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = field;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("Invalid state!");
        }

        this.state = state;
    }

    public WebField getField() {
        return field;
    }

    public void setField(WebField field) {
        if (field == null) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = field;
    }
}
