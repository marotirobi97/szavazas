package com.szavazas.szavazas.entity;

import com.szavazas.szavazas.enums.EljarasEnum;
import com.szavazas.szavazas.enums.SzavazasTipusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "szavazas")
public class SzavazasEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "idopont", nullable = false)
    private LocalDateTime idopont;

    @Column(name = "targy", nullable = false)
    private String targy;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipus", nullable = false)
    private SzavazasTipusEnum tipus;

    @Enumerated(EnumType.STRING)
    @Column(name = "eljaras", nullable = false)
    private EljarasEnum eljaras;

    @Column(name = "elnok", nullable = false)
    private String elnok;

    @OneToMany(mappedBy = "szavazas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SzavazatEntity> szavazatok = new ArrayList<>();
}
