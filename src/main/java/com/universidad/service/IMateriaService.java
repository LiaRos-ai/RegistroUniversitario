package com.universidad.service;

import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;

import java.util.List;

public interface IMateriaService {
    List<MateriaDTO> obtenerTodasLasMaterias();
    MateriaDTO obtenerMateriaPorId(Long id);
    MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico);
    MateriaDTO crearMateria(MateriaDTO materia);
    MateriaDTO actualizarMateria(Long id, MateriaDTO materia);
    void eliminarMateria(Long id);
    List<UnidadTematica> obtenerUnidadesPorMateria(Long id);
    List<Materia> obtenerMateriasConUnidades();
    void reemplazarUnidades(Long materiaId, List<UnidadTematica> nuevasUnidades);
}
