package com.example.tictactoe.datasource.converter;

import com.example.tictactoe.datasource.model.DataField;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FieldJsonConverter implements AttributeConverter<DataField, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DataField attribute) {
        if (attribute == null) return null;

        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DataField convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;

        try {
            return mapper.readValue(dbData, DataField.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
