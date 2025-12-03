package com.example.tictactoe.domain.model.field;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private int[][] field;

    public Field() {
        field = new int[3][3];
    }

    public Field(Field src) {
        if (src == null) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = src.field;
    }

    public Field(int[][] src) {
        if (src == null || src.length == 0) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = src;
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

    public List<Field> getNextFields(int symbol) {
        List<Field> nextFields = new ArrayList<>();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == 0) {
                    int[][] newField = deepCopy();
                    newField[i][j] = symbol;
                    nextFields.add(new Field(newField));
                }
            }
        }

        return nextFields;
    }

    private boolean hasEmptyPlace() {
        for (int[] rows : field) {
            for (int place : rows) {
                if (place == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasExtraSymbols(List<Integer> symbolsToCheck) {
        for (int[] rows : field) {
            for (int place : rows) {
                if (place != 0 && !symbolsToCheck.contains(place)) {
                    return true;
                }
            }
        }

        return false;
    }

    private FieldState check(int iStart, int jStart, int di, int dj) {
        if (field[iStart][jStart] != 0) {
            int i = iStart + di;
            int j = jStart + dj;

            while (i < field.length && j < field.length && i >= 0 && j >= 0) {
                if (field[i][j] != field[iStart][jStart]) {
                    return FieldState.IN_PROGRESS;
                }

                i += di;
                j += dj;
            }

            return field[iStart][jStart] == 1 ? FieldState.FILLED_BY_X : FieldState.FILLED_BY_O;
        }

        return FieldState.IN_PROGRESS;
    }

    public boolean equals(Field other) {
        if (other == null) {
            throw new IllegalArgumentException("Other field is null!");
        }

        if (this == other || this.field == other.field) {
            return true;
        }

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (other.field[i][j] != field[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getLessSymbol() {
        int count1 = 0;
        int count2 = 0;

        for (int[] ints : field) {
            for (int j = 0; j < field.length; j++) {
                if (ints[j] == 1) {
                    count1++;
                } else if (ints[j] == 2) {
                    count2++;
                }
            }
        }

        return count2 < count1 ? 2 : 1;
    }

    public boolean hasValidStep(Field other, int userMark) {
        if (other == null) {
            throw new IllegalArgumentException("Other field is null!");
        }

        boolean foundStep = false;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (other.field[i][j] != field[i][j]) {
                    if (!foundStep && other.field[i][j] == 0) {
                        foundStep = true;

                        if (field[i][j] != userMark) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundStep;
    }

    public FieldState getFieldState() {
        FieldState result = check(0, 0, 1, 1);

        if (result != FieldState.IN_PROGRESS) return result;

        result = check(0, field.length - 1, 1, -1);

        if (result != FieldState.IN_PROGRESS) return result;

        for (int i = 0; i < field.length; i++) {
            result = check(i, 0, 0, 1);
            if (result != FieldState.IN_PROGRESS) return result;
        }

        for (int i = 0; i < field.length; i++) {
            result = check(0, i, 1, 0);
            if (result != FieldState.IN_PROGRESS) return result;
        }

        return hasEmptyPlace() ? FieldState.IN_PROGRESS : FieldState.IS_FULL;
    }
}
