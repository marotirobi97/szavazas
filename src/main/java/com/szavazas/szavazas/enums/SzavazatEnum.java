package com.szavazas.szavazas.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SzavazatEnum {
    IGEN("I"),
    NEM("N"),
    TARTOZKODAS("t");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SzavazatEnum fromValue(String value) {
        for (SzavazatEnum szavazat : values()) {
            if (szavazat.value.equalsIgnoreCase(value)) {
                return szavazat;
            }
        }
        throw new IllegalArgumentException("Érvénytelen szavazat érték: " + value);
    }
}
