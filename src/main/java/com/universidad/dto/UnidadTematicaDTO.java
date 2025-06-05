package com.universidad.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UnidadTematicaDTO {
    private Long id;
    private String id_materia;
    private String nombre_unidad;
    private String descripcion;
    private String contenido;
}