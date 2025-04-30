package com.universidad.service;

import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.Materia;
import java.util.List;

public interface IMateriaService {
    List<MateriaDTO> obtenerTodasLasMaterias();
    MateriaDTO obtenerMateriaPorId(Long id);
    MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico);
    MateriaDTO crearMateria(MateriaDTO materia);
    MateriaDTO actualizarMateria(Long id, MateriaDTO materia);
    void eliminarMateria(Long id);

    /**
     * Reemplaza todas las unidades temáticas de una materia.
     * @param id ID de la materia
     * @param unidades Lista de nuevas unidades temáticas
     * @return La materia actualizada con sus nuevas unidades temáticas
     */
    MateriaDTO reemplazarUnidadesTematicas(Long id, List<UnidadTematicaDTO> unidades);
}
