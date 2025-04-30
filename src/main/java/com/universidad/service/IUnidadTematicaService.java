package com.universidad.service;

import com.universidad.dto.UnidadTematicaDTO;
import java.util.List;

public interface IUnidadTematicaService {

    /**
     * Obtiene todas las unidades temáticas.
     * @return Lista de unidades temáticas
     */
    List<UnidadTematicaDTO> obtenerTodasLasUnidades();

    /**
     * Obtiene una unidad temática por su ID.
     * @param id ID de la unidad temática
     * @return La unidad temática encontrada o null
     */
    UnidadTematicaDTO obtenerUnidadPorId(Long id);

    /**
     * Obtiene todas las unidades temáticas de una materia.
     * @param materiaId ID de la materia
     * @return Lista de unidades temáticas
     */
    List<UnidadTematicaDTO> obtenerUnidadesPorMateriaId(Long materiaId);

    /**
     * Crea una nueva unidad temática.
     * @param unidadDTO Datos de la unidad temática
     * @return La unidad temática creada
     */
    UnidadTematicaDTO crearUnidad(UnidadTematicaDTO unidadDTO);

    /**
     * Actualiza una unidad temática existente.
     * @param id ID de la unidad temática
     * @param unidadDTO Datos actualizados
     * @return La unidad temática actualizada
     */
    UnidadTematicaDTO actualizarUnidad(Long id, UnidadTematicaDTO unidadDTO);

    /**
     * Elimina una unidad temática.
     * @param id ID de la unidad temática
     */
    void eliminarUnidad(Long id);
}
