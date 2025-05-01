package com.universidad.service;

import com.universidad.dto.UnidadTematicaDTO;

import java.util.List;

/**
 * Servicio para operaciones relacionadas con Unidades Temáticas.
 */
public interface IUnidadTematicaService {

    /**
     * Obtiene todas las unidades temáticas asociadas a una materia.
     *
     * @param materiaId ID de la materia.
     * @return Lista de DTOs de unidades temáticas.
     */
    List<UnidadTematicaDTO> obtenerPorMateria(Long materiaId);

    // TAREA GRUPO
}
