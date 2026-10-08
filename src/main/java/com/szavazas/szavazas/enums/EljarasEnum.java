package com.szavazas.szavazas.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EljarasEnum {
    NORMAL("n"),
    SURGOSSEGI("s"),
    KIVETELES("k"),
    ELTERO("e");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static EljarasEnum fromValue(String value) {
        for (EljarasEnum eljaras : values()) {
            if (eljaras.value.equalsIgnoreCase(value)) {
                return eljaras;
            }
        }
        throw new IllegalArgumentException("Érvénytelen eljárási típus: " + value);
    }
}
