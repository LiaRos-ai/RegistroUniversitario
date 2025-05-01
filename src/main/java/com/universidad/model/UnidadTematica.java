package com.universidad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unidad_tematica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadTematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    // Relación Many-to-One con Materia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia")
    @JsonBackReference // Uso
    private Materia materia;
}