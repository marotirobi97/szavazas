package com.szavazas.szavazas.response;

import com.szavazas.szavazas.enums.SzavazatEnum;
import lombok.Builder;

@Builder
public record SzavazatResponse(
        SzavazatEnum szavazat
) {
}
