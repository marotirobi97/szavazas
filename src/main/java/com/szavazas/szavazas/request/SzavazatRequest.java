package com.szavazas.szavazas.request;

import com.szavazas.szavazas.enums.SzavazatEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SzavazatRequest(
        @NotBlank(message = "A képviselő azonosítója nem lehet üres")
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Kepviselo1")
        String kepviselo,

        @NotBlank(message = "A szavazat megadása kötelező (i, n, t)")
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "e")
        SzavazatEnum szavazat
) {
}
