package com.universidad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "unidad_tematica") // Nombre de la tabla en la base de datos
public class UnidadTematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad_tematica")
    private Long id;

    @Column(name = "nombre_unidad", nullable = false, length = 100)
    private String nombreUnidad;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    @JsonBackReference // Evita los ciclos infinitos
    private Materia materia;

    @Version
    private Long version;
    // Getters y Setters generados por Lombok
}
