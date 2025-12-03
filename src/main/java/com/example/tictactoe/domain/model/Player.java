package com.example.tictactoe.domain.model;

public enum Player {
    X(1), O(2);

    private final int code;

    Player(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Player fromInt(int i) {
        return switch (i) {
            case 1 -> X;
            case 2 -> O;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

    public static Player fromString(String s) {
        return switch (s.toLowerCase()) {
            case "x" -> X;
            case "o" -> O;
            default -> throw new IllegalStateException("Unexpected value: " + s);
        };
    }
}
