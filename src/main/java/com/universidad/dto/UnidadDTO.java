package com.universidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer duracionHoras;
    private Long materiaId;
}