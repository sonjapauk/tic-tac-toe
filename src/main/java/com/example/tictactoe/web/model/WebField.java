package com.example.tictactoe.web.model;

public class WebField {
    private int[][] field;

    public WebField() {
        field = new int[3][3];
    }

    public WebField(int[][] field) {
        if (field == null || field.length == 0) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = field;
    }

    public WebField(WebField src) {
        if (src == null) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = src.field;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        if (field == null || field.length == 0) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = field;
    }

    public int[][] deepCopy() {
        int[][] fieldCopy = new int[field.length][field[0].length];

        for (int i = 0; i < field.length; ++i) {
            System.arraycopy(field[i], 0, fieldCopy[i], 0, field[i].length);
        }

        return fieldCopy;
    }
}
