package com.yegorov.Lab2.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessages {
    VALIDATION("Ошибка валидации"),
    UNKNOWN("Неподдерживаемая ошибка"),
    UNSUPPORTED("Произошла непредвиденная ошибка"),
    EMPTY("");

    private final String description;

    ErrorMessages(String description) {
        this.description = description;
    }

    @JsonValue
    public String getName() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
