package com.szavazas.szavazas.repository;

import com.szavazas.szavazas.entity.SzavazasEntity;
import com.szavazas.szavazas.enums.SzavazasTipusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SzavazasRepository extends JpaRepository<SzavazasEntity, String> {

    boolean existsByIdopont(LocalDate idopont);

    @Query("""
        SELECT szavazas FROM SzavazasEntity szavazas
        WHERE szavazas.tipus = :tipus
          AND szavazas.idopont < :idopont
        ORDER BY szavazas.idopont DESC
        """)
    Optional<SzavazasEntity> findPreviousJelenletiSzavazas(SzavazasTipusEnum tipus, LocalDate idopont);

    @Query("""
        SELECT szavazas FROM SzavazasEntity szavazas
        WHERE szavazas.idopont >= :startOfDay AND szavazas.idopont < :endOfDay
        ORDER BY szavazas.idopont ASC
        """)
    List<SzavazasEntity> findByNap(LocalDate startOfDay, LocalDate endOfDay);

}
