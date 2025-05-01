package com.universidad.service;

import com.universidad.dto.UnidadTematicaDTO;
import java.util.List;

public interface IUnidadTematicaService {

    // Obtener todas las unidades temáticas
    List<UnidadTematicaDTO> obtenerTodasLasUnidades();

    // Obtener las unidades temáticas de una materia específica
    List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId);

    // Crear una nueva unidad temática
    UnidadTematicaDTO crearUnidadTematica(UnidadTematicaDTO unidadTematica);

    // Actualizar una unidad temática existente
    UnidadTematicaDTO actualizarUnidadTematica(Long id, UnidadTematicaDTO unidadTematica);

    // Eliminar una unidad temática por su ID
    void eliminarUnidadTematica(Long id);

    // Otros métodos según sea necesario
}
