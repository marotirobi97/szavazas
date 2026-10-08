package com.szavazas.szavazas.service;

import com.szavazas.szavazas.entity.SzavazasEntity;
import com.szavazas.szavazas.entity.SzavazatEntity;
import com.szavazas.szavazas.enums.EredmenyEnum;
import com.szavazas.szavazas.enums.SzavazasTipusEnum;
import com.szavazas.szavazas.enums.SzavazatEnum;
import com.szavazas.szavazas.exception.NotFoundDomainBaseException;
import com.szavazas.szavazas.repository.SzavazasRepository;
import com.szavazas.szavazas.repository.SzavazatRepository;
import com.szavazas.szavazas.request.SzavazasRequest;
import com.szavazas.szavazas.response.*;
import com.szavazas.szavazas.validator.SzavazasValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SzavazasService {

    private static final int OSSZES_KEPVISELO_SZAMA = 200;
    public static final String A_MEGADOTT_KEPVISELO_NEM_VETT_RESZT_EZEN_A_SZAVAZASON = "A megadott képviselő nem vett részt ezen a szavazáson!";
    public static final String A_MEGADOTT_AZONOSITOVAL_NEM_TALÁLHATO_SZAVAZAS = "A megadott azonosítóval nem található szavazás!";

    private final SzavazasRepository szavazasRepository;
    private final SzavazatRepository szavazatRepository;
    private final SzavazasMapper szavazasMapper;
    private final SzavazasValidator szavazasValidator;

    @Transactional
    public SzavazasIdResponse szavazasMentes(SzavazasRequest request) {
        szavazasValidator.validateSzavazasMentes(request);

        SzavazasEntity szavazasEntity = szavazasMapper.toEntity(request);

        szavazasRepository.save(szavazasEntity);

        return new SzavazasIdResponse(szavazasEntity.getId());
    }

    @Transactional
    public SzavazatResponse szavazatLekerese(String szavazasId, String kepviselo) {
        szavazasValidator.validateSzavazasId(szavazasId);

        SzavazatEntity szavazatEntity = getSzavazatEntity(szavazasId, kepviselo);

        return SzavazatResponse.builder()
                .szavazat(szavazatEntity.getSzavazat())
                .build();
    }

    @Transactional
    public EredmenyResponse szavazasEredmenyKiszamolasa(String szavazasId) {
        SzavazasEntity szavazas = getSzavazasEntity(szavazasId);

        int igenekSzama = countVotesByType(szavazas, SzavazatEnum.IGEN);
        int nemekSzama = countVotesByType(szavazas, SzavazatEnum.NEM);
        int tartozkodasokSzama = countVotesByType(szavazas, SzavazatEnum.TARTOZKODAS);

        int figyelembeVettKepviselokSzama = getFigyelembeVettKepviselokSzama(szavazas);

        boolean elfogadva = isElfogadva(szavazas.getTipus(), igenekSzama, figyelembeVettKepviselokSzama);

        EredmenyEnum eredmeny = elfogadva ? EredmenyEnum.F : EredmenyEnum.U;

        return EredmenyResponse.builder()
                .eredmeny(eredmeny)
                .kepviselokSzama(figyelembeVettKepviselokSzama)
                .igenekSzama(igenekSzama)
                .nemekSzama(nemekSzama)
                .tartozkodasokSzama(tartozkodasokSzama)
                .build();
    }

    @Transactional
    public NapiSzavazasokResponse getNapiSzavazasok(LocalDate nap) {
        LocalDate startOfDay = nap.atStartOfDay(ZoneOffset.UTC).toLocalDate();
        LocalDate endOfDay = nap.plusDays(1).atStartOfDay(ZoneOffset.UTC).toLocalDate();

        List<SzavazasEntity> szavazasok = szavazasRepository.findByNap(startOfDay, endOfDay);

        List<NapiSzavazasokResponse.NapiSzavazasDto> napiSzavazasDtos = szavazasok.stream()
                .map(this::getNapiSzavazasDto)
                .toList();

        return NapiSzavazasokResponse.builder()
                .szavazasok(napiSzavazasDtos)
                .build();
    }

    @Transactional
    public KepviseloReszvetelAtlagResponse getKepviseloReszvetelAtlag(String kepviselo, LocalDate from, LocalDate until) {
        long napokSzama = ChronoUnit.DAYS.between(from, until) + 1;

        long kepviseloSzavazatai = szavazatRepository.countKepviseloSzavazataiInPeriod(kepviselo, from, until);

        double atlag = BigDecimal.valueOf((double) kepviseloSzavazatai / napokSzama)
                .setScale(2, RoundingMode.HALF_UP).doubleValue();

        return KepviseloReszvetelAtlagResponse.builder().atlag(atlag).build();
    }

    private SzavazatEntity getSzavazatEntity(String szavazasId, String kepviselo) {
        return szavazatRepository.findBySzavazas_IdAndKepviseloIgnoreCase(szavazasId, kepviselo)
                .orElseThrow(() -> new NotFoundDomainBaseException(A_MEGADOTT_KEPVISELO_NEM_VETT_RESZT_EZEN_A_SZAVAZASON));
    }

    private SzavazasEntity getSzavazasEntity(String szavazasId) {
        return szavazasRepository.findById(szavazasId)
                .orElseThrow(() -> new NotFoundDomainBaseException(A_MEGADOTT_AZONOSITOVAL_NEM_TALÁLHATO_SZAVAZAS));
    }

    private NapiSzavazasokResponse.NapiSzavazasDto getNapiSzavazasDto(SzavazasEntity szavazas) {
        int igenekSzama = countVotesByType(szavazas, SzavazatEnum.IGEN);
        int figyelembeVettLetszam = getFigyelembeVettKepviselokSzama(szavazas);
        boolean elfogadva = isElfogadva(szavazas.getTipus(), igenekSzama, figyelembeVettLetszam);

        return NapiSzavazasokResponse.NapiSzavazasDto.builder()
                .idopont(szavazas.getIdopont())
                .targy(szavazas.getTargy())
                .tipus(szavazas.getTipus())
                .eljaras(szavazas.getEljaras())
                .elnok(szavazas.getElnok())
                .eredmeny(elfogadva ? EredmenyEnum.F : EredmenyEnum.U)
                .kepviselokSzama(figyelembeVettLetszam)
                .szavazatok(getSzavazatok(szavazas.getSzavazatok()))
                .build();
    }

//    • jelenlét: csak a jelenlevő képviselők létszámának megállapítására szolgál, eredménye mindig elfogadott
//    • egyszerű: eredménye elfogadott, ha a jelenlevő képviselők több mint fele igennel szavazott, egyébként elutasított.
//    • minősített: eredménye elfogadott, ha az összes képviselő több mint fele igennel szavazott.
    private boolean isElfogadva(SzavazasTipusEnum tipusEnum, int igenekSzama, int figyelembeVettKepviselokSzama) {
        return tipusEnum.equals(SzavazasTipusEnum.JELENLET) || igenekSzama > (figyelembeVettKepviselokSzama / 2.0);
    }

    private int getFigyelembeVettKepviselokSzama(SzavazasEntity szavazasEntity) {
        return switch (szavazasEntity.getTipus()) {
            case JELENLET -> szavazasEntity.getSzavazatok().size();
            case MINOSITETT -> OSSZES_KEPVISELO_SZAMA;
            case EGYSZERU -> szavazasRepository
                    .findPreviousJelenletiSzavazas(SzavazasTipusEnum.JELENLET, szavazasEntity.getIdopont())
                    .map(SzavazasEntity::getSzavazatok)
                    .map(List::size)
                    .orElse(OSSZES_KEPVISELO_SZAMA);
        };
    }

    private int countVotesByType(SzavazasEntity szavazas, SzavazatEnum szavazatTypeEnum) {
        return (int) szavazas.getSzavazatok().stream()
                .map(SzavazatEntity::getSzavazat)
                .filter(szavazatEnum -> szavazatEnum == szavazatTypeEnum)
                .count();
    }

    private List<NapiSzavazasokResponse.NapiSzavazasDto.SzavazatDto> getSzavazatok(List<SzavazatEntity> szavazatok) {
        return szavazatok.stream()
                .map(this::getSzavazatDto)
                .toList();
    }

    private NapiSzavazasokResponse.NapiSzavazasDto.SzavazatDto getSzavazatDto(SzavazatEntity szavazat) {
        return NapiSzavazasokResponse.NapiSzavazasDto.SzavazatDto.builder()
                .kepviselo(szavazat.getKepviselo())
                .szavazat(szavazat.getSzavazat())
                .build();
    }

}
