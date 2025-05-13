package com.universidad.service;

import com.universidad.dto.UnidadTematicaDTO;
import java.util.List;

public interface UnidadTematicaService {
    UnidadTematicaDTO crearUnidad(UnidadTematicaDTO unidadDTO);
    List<UnidadTematicaDTO> listarPorMateria(Long idMateria);
    void eliminarUnidad(Long idUnidad);
}
