package com.szavazas.szavazas.response;

import com.szavazas.szavazas.enums.EljarasEnum;
import com.szavazas.szavazas.enums.EredmenyEnum;
import com.szavazas.szavazas.enums.SzavazasTipusEnum;
import com.szavazas.szavazas.enums.SzavazatEnum;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record NapiSzavazasokResponse(
        List<NapiSzavazasDto> szavazasok
) {
    @Builder(toBuilder = true)
    public record NapiSzavazasDto(
            LocalDateTime idopont,
            String targy,
            SzavazasTipusEnum tipus,
            EljarasEnum eljaras,
            String elnok,
            EredmenyEnum eredmeny,
            int kepviselokSzama,
            List<SzavazatDto> szavazatok
    ) {
        @Builder(toBuilder = true)
        public record SzavazatDto(
                String kepviselo,
                SzavazatEnum szavazat
        ) {
        }
    }
}
