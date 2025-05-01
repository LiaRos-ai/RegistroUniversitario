package com.universidad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "unidad_tematica")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadTematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad")
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    @JsonBackReference // Para evitar referencia circular al serializar JSON
    private Materia materia;
}
