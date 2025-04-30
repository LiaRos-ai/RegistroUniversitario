package com.universidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadTematicaDTO implements Serializable {

    private Long id;
    private String titulo;
    private String descripcion;
    private Integer orden;
    private Long materiaId; // Solo el ID de la materia para evitar referencias circulares
}
