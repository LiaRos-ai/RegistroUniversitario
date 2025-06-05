package com.universidad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "UnidadTematica") // Nombre de la tabla en la base de datos
public class UnidadTematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidadtematica")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    @JsonBackReference // Evita el ciclo infinito al serializar hacia JSON
    private Materia materia;

    @Column(name = "nombre_unidad", nullable = false)
    private String nombre_unidad;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "contenido", nullable = false)
    private String contenido;
}