package com.universidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir datos de una Unidad Temática.
 * Contiene información básica como el id, título y descripción.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnidadTematicaDTO {

    /**
     * Identificador único de la unidad temática.
     */
    private Long id;

    /**
     * Título de la unidad temática.
     */
    private String titulo;

    /**
     * Descripción general de la unidad temática.
     */
    private String descripcion;

    // TAREA GRUPO
}
