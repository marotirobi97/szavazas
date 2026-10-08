package com.szavazas.szavazas.entity;

import com.szavazas.szavazas.enums.SzavazatEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "szavazat")
public class SzavazatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "szavazas_id", nullable = false)
    private SzavazasEntity szavazas;

    @Column(nullable = false)
    private String kepviselo;

    @Enumerated(EnumType.STRING)
    @Column(name = "szavazat", nullable = false)
    private SzavazatEnum szavazat;
}
