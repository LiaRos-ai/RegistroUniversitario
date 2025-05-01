package com.universidad.service;

import com.universidad.model.UnidadTematica;
import java.util.List;

public interface IUnidadTematicaService {
    List<UnidadTematica> listarPorMateria(Long idMateria);
}
