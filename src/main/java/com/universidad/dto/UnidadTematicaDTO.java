package com.universidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadTematicaDTO {

    private Long id; // ID de la unidad temática
    private String tema; // Nombre del tema
    private String descripcion; // Descripción del tema
    private Long materiaId; // ID de la materia asociada
}
