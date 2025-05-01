package com.universidad.service;

import java.util.List;

import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;

public interface IUnidadTematicaService {
    UnidadTematica crearUnidadTematica(UnidadTematica unidadTematica);
    List<UnidadTematica> obtenerUnidadTematicaPorMateria(Long materiaID);
    UnidadTematica obtenerUnidadTematicaPorID(Long id);
    void eliminarUnidadTematica(Long id);
    public UnidadTematica actualizarUnidadTematica(Long id, UnidadTematica unidadTematica);
    public List<Materia> obtenerTodaUnidadTematica();
}
