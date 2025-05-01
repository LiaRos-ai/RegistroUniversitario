package com.universidad.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadTematicaDTO {
    private Long id;
    private String titulo;
    private Long idMateria;
}
