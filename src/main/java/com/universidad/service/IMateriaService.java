package com.universidad.service;

import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadTematicaDTO;
import java.util.List;

public interface IMateriaService {

    // Obtener todas las materias
    List<MateriaDTO> obtenerTodasLasMaterias();

    // Obtener una materia por su ID
    MateriaDTO obtenerMateriaPorId(Long id);

    // Obtener una materia por su código único
    MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico);

    // Crear una nueva materia
    MateriaDTO crearMateria(MateriaDTO materia);

    // Actualizar una materia existente
    MateriaDTO actualizarMateria(Long id, MateriaDTO materia);

    // Eliminar una materia por su ID
    void eliminarMateria(Long id);

    List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId);

    // Actualizar las unidades temáticas de una materia
    MateriaDTO actualizarUnidadesPorMateria(Long id, List<UnidadTematicaDTO> nuevasUnidades);

    
}
