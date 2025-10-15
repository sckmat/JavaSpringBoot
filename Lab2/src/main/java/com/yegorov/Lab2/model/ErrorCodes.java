package com.yegorov.Lab2.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCodes {
    VALIDATION("ValidationException"),
    UNKNOWN("UnknownException"),
    UNSUPPORTED("UnsupportedCodeException"),
    EMPTY("");

    private final String name;

    ErrorCodes(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
