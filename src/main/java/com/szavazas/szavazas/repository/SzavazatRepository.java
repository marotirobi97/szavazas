package com.szavazas.szavazas.repository;

import com.szavazas.szavazas.entity.SzavazatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface SzavazatRepository extends JpaRepository<SzavazatEntity, Long> {

    Optional<SzavazatEntity> findBySzavazas_IdAndKepviseloIgnoreCase(String szavazasId, String kepviselo);

    @Query("""
        SELECT COUNT(sz)
        FROM SzavazatEntity sz
        INNER JOIN SzavazasEntity sza ON sz.szavazas.id = sza.id
        WHERE sz.kepviselo = :kepviselo
          AND sz.szavazas.idopont BETWEEN :from AND :until
          AND sza.tipus != "JELENLET"
        """)
    long countKepviseloSzavazataiInPeriod(String kepviselo, LocalDate from, LocalDate until);

}
