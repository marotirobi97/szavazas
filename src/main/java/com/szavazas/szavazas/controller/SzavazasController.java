package com.szavazas.szavazas.controller;

import com.szavazas.szavazas.request.SzavazasRequest;
import com.szavazas.szavazas.response.*;
import com.szavazas.szavazas.service.SzavazasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/szavazasok")
@RequiredArgsConstructor
public class SzavazasController implements SwaggerConfiguredSzavazasController{

    private final SzavazasService szavazasService;

    // 1 Egy szavazás adatainak elmentése
    @Override
    @PostMapping("/szavazas")
    @ResponseStatus(HttpStatus.CREATED)
    public SzavazasIdResponse save(@Valid @RequestBody SzavazasRequest request) {
        return szavazasService.szavazasMentes(request);
    }

    //2 Egy képviselő adott szavazáson leadott szavazatának lekérdezése
    @Override
    @GetMapping("/szavazat/{szavazasId}/{kepviselo}")
    @ResponseStatus(HttpStatus.OK)
    public SzavazatResponse getKepviseloSzavazat(@PathVariable String szavazasId, @PathVariable String kepviselo) {
        return szavazasService.szavazatLekerese(szavazasId, kepviselo);
    }

    //3 A szavazás eredményének (elfogadott/elutasított) kiszámolása
    @Override
    @GetMapping("/eredmeny/{szavazasId}")
    public EredmenyResponse getSzavazasEredmeny(@PathVariable String szavazasId) {
        return szavazasService.szavazasEredmenyKiszamolasa(szavazasId);
    }

    //4 Adott napra a szavazások és eredményeik lekérdezése
    @Override
    @GetMapping("/napi-szavazasok/{nap}")
    public NapiSzavazasokResponse getNapiSzavazasok(LocalDate nap) {
        return szavazasService.getNapiSzavazasok(nap);
    }

    //5.1 Kimutatások készítése - Részvétel
    @Override
    @GetMapping("/kepviselo-reszvetel-atlag/{kepviselo}/{from}/{until}")
    public KepviseloReszvetelAtlagResponse getKepviseloReszvetelAtlag(String kepviselo, LocalDate from, LocalDate until) {
        return szavazasService.getKepviseloReszvetelAtlag(kepviselo, from, until);
    }

}
