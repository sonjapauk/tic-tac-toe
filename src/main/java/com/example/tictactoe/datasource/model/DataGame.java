package com.example.tictactoe.datasource.model;

import com.example.tictactoe.datasource.converter.FieldJsonConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class DataGame {
    @Convert(converter = FieldJsonConverter.class)
    @Lob
    private DataField field;

    @Id
    private String currentID;

    public DataGame() {
        field = new DataField();
    }

    public DataGame(DataField field, String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Invalid Game ID!");
        }

        if (field == null) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = field;
        currentID = id;
    }

    public DataField getField() {
        return field;
    }

    public String getCurrentID() {
        return currentID;
    }

    public void setField(DataField field) {
        if (field == null) {
            throw new IllegalArgumentException("Invalid field!");
        }

        this.field = field;
    }

    public void setCurrentID(String currentID) {
        if (currentID == null || currentID.isBlank()) {
            throw new IllegalArgumentException("Invalid Game ID!");
        }

        this.currentID = currentID;
    }

    public DataField copyField() {
        return new DataField(field.copy());
    }
}
