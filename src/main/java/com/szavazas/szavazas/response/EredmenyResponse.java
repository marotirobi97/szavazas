package com.szavazas.szavazas.response;

import com.szavazas.szavazas.enums.EredmenyEnum;
import lombok.Builder;

@Builder
public record EredmenyResponse(
        EredmenyEnum eredmeny,
        int kepviselokSzama,
        int igenekSzama,
        int nemekSzama,
        int tartozkodasokSzama
) {
}
