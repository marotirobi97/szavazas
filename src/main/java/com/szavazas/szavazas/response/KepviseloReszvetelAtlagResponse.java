package com.szavazas.szavazas.response;

import lombok.Builder;

@Builder
public record KepviseloReszvetelAtlagResponse(
        double atlag
) {
}
