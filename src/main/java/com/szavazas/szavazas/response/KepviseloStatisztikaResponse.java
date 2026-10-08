package com.szavazas.szavazas.response;

public record KepviseloStatisztikaResponse(
        String kepviselo,
        long osszesSzavazas,
        long reszvetelSzama,
        double reszveteliArany,
        long igenekSzama,
        long nemekSzama,
        long tartozkodasokSzama
) {
}
