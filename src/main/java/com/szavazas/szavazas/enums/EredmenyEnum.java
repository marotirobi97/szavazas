package com.szavazas.szavazas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EredmenyEnum {
    F("F"),
    U("U");

    private final String ertek;

    @JsonValue
    public String getErtek() {
        return ertek;
    }
}
