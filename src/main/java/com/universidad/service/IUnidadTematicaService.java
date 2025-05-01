package com.universidad.service;

import com.universidad.dto.UnidadTematicaDTO;
import java.util.List;

public interface IUnidadTematicaService {
    List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId);
    UnidadTematicaDTO crearUnidad(UnidadTematicaDTO unidadDTO, Long materiaId);
    // Otros métodos
}