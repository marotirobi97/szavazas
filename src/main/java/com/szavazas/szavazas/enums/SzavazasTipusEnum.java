package com.szavazas.szavazas.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SzavazasTipusEnum {
    JELENLET("j"),
    EGYSZERU("e"),
    MINOSITETT("m");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SzavazasTipusEnum fromValue(String value) {
        for (SzavazasTipusEnum tipus : values()) {
            if (tipus.value.equalsIgnoreCase(value)) {
                return tipus;
            }
        }
        throw new IllegalArgumentException("Érvénytelen szavazás típus: " + value);
    }
}
