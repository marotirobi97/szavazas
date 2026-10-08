package com.szavazas.szavazas.service;

import com.szavazas.szavazas.entity.SzavazasEntity;
import com.szavazas.szavazas.entity.SzavazatEntity;
import com.szavazas.szavazas.request.SzavazasRequest;
import com.szavazas.szavazas.request.SzavazatRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SzavazasMapper {

    public SzavazasEntity toEntity(SzavazasRequest request) {
        String szavazasId = UUID.randomUUID().toString();

        SzavazasEntity szavazasEntity = buildSzavazasEntity(request, szavazasId);

        List<SzavazatEntity> szavazatEntities = request.szavazatok().stream()
                .map(szavazatReq -> buildSzavazatEntity(szavazatReq, szavazasEntity))
                .toList();

        szavazasEntity.setSzavazatok(szavazatEntities);

        return szavazasEntity;
    }

    private SzavazasEntity buildSzavazasEntity(SzavazasRequest request, String szavazasId) {
        return SzavazasEntity.builder()
                .id(szavazasId)
                .idopont(request.idopont())
                .targy(request.targy())
                .tipus(request.tipus())
                .eljaras(request.eljaras())
                .elnok(request.elnok())
                .build();
    }

    private SzavazatEntity buildSzavazatEntity(SzavazatRequest szavazatReq, SzavazasEntity szavazasEntity) {
        return SzavazatEntity.builder()
                .kepviselo(szavazatReq.kepviselo())
                .szavazat(szavazatReq.szavazat())
                .szavazas(szavazasEntity)
                .build();
    }
}
