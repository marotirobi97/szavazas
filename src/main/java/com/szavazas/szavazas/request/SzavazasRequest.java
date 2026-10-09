package com.szavazas.szavazas.request;

import com.szavazas.szavazas.enums.EljarasEnum;
import com.szavazas.szavazas.enums.SzavazasTipusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record SzavazasRequest(
        @NotBlank(message = "Az időpont megadása kötelező")
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2026-10-01")
        LocalDateTime idopont,

        @NotBlank(message = "A tárgy nem lehet üres")
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Szavazás 01")
        String targy,

        @NotBlank(message = "A típus megadása kötelező (j, e, m)")
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "e")
        SzavazasTipusEnum tipus,

        @NotBlank(message = "Az eljárás megadása kötelező (n, s, k, e)")
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "n")
        EljarasEnum eljaras,

        @NotBlank(message = "Az elnök megadása kötelező")
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Kepviselo1")
        String elnok,

        @Valid
        @NotEmpty(message = "A szavazatok listája nem lehet üres")
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, type = "array", example = "[{\"kepviselo\":\"Kepviselo1\",\"szavazat\":\"i\"},{\"kepviselo\":\"Kepviselo2\",\"szavazat\":\"n\"},{\"kepviselo\":\"Kepviselo3\",\"szavazat\":\"i\"}]")
        List<SzavazatRequest> szavazatok
) {
}
