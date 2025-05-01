package com.universidad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad que representa una Unidad Temática dentro de una Materia.
 * Cada unidad está asociada a una sola materia, pero una materia puede tener muchas unidades.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "unidad_tematica") 
public class UnidadTematica {

    /**
     * Identificador único de la unidad temática.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título o nombre de la unidad temática.
     */
    @Column(nullable = false)
    private String titulo;

    /**
     * Descripción o contenido general de la unidad temática.
     */
    @Column(length = 1000)
    private String descripcion;

    /**
     * Materia a la que pertenece esta unidad temática.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    @JsonBackReference
    private Materia materia;

    // TAREA GRUPO
}
