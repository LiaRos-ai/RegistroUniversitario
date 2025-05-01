package com.universidad.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "unidad_tematica")
public class UnidadTematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad_tematica")
    private Long id;

    @Column(name = "tema", nullable = false, length = 255)
    private String tema;

    @Column(name = "descripcion", length = 500)
    private String descripcion; // Descripción de la unidad temática

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    @JsonBackReference  // Evitar el ciclo infinito con @JsonManagedReference en Materia
    private Materia materia;
}
