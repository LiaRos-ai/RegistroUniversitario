package com.universidad.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadTematicaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer orden;
    private Long materiaId; // Solo necesitamos el ID de la materia
}