package com.szavazas.szavazas.controller;


import com.szavazas.szavazas.request.SzavazasRequest;
import com.szavazas.szavazas.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Tag(name = "Szavazas endpoints", description = "Parlamenti szavazások endpointok")
interface SwaggerConfiguredSzavazasController {

    @Operation(
            summary = "Új szavazás rögzítése",
            description = "Létrehoz egy új szavazást a megadott adatokkal és képviselői szavazatokkal."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sikeres mentés, visszaadja a szavazás azonosítóját"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validációs hiba (pl. hiányzó elnöki szavazat, duplikált képviselői szavazat vagy meglévő időpont)"
            )
    })
    SzavazasIdResponse save(SzavazasRequest request);

    @Operation(summary = "Képviselői szavazat lekérdezése", description = "Visszaadja egy adott képviselő szavazatát egy konkrét szavazáson.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés"),
            @ApiResponse(responseCode = "404", description = "A szavazás vagy a képviselő szavazata nem található")
    })
    SzavazatResponse getKepviseloSzavazat(String szavazasId, String kepviselo);

    @Operation(
            summary = "Szavazás eredményének kiszámolása",
            description = "Kiszámítja és visszaadja egy adott szavazás eredményét (F: elfogadott, U: elutasított) és a szavazási statisztikákat."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres kiszámítás"),
            @ApiResponse(responseCode = "404", description = "A megadott azonosítóval nem található szavazás")
    })
    EredmenyResponse getSzavazasEredmeny(String szavazasId);

    @Operation(
            summary = "Adott napra a szavazások és eredményeik lekérdezése",
            description = "Visszaadja a megadott napon megtartott szavazásokat, azok eredményeit és a leadott szavazatokat."
    )
    NapiSzavazasokResponse getNapiSzavazasok(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate nap);

    @Operation(
            summary = "Képviselő átlagos részvételének lekérdezése",
            description = "Kiszámítja az adott képviselő részvételi arányát a megadott 'tol' és 'ig' dátumok közötti szavazásokon."
    )
    KepviseloReszvetelAtlagResponse getKepviseloReszvetelAtlag(String kepviselo,
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate until);

}
