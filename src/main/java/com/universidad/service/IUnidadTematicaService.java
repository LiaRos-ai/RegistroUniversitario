package com.universidad.service;

import com.universidad.dto.UnidadTematicaDTO;

import java.util.List;

public interface IUnidadTematicaService {
    List<UnidadTematicaDTO> listarPorMateria(Long materiaId);
}
