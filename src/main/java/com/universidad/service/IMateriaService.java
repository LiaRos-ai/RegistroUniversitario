package com.universidad.service;

import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadDTO;
import com.universidad.model.Materia;
import java.util.List;

public interface IMateriaService {
    List<MateriaDTO> obtenerTodasLasMaterias();
    MateriaDTO obtenerMateriaPorId(Long id);
    MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico);
    MateriaDTO crearMateria(MateriaDTO materia);
    MateriaDTO actualizarMateria(Long id, MateriaDTO materia);
    void eliminarMateria(Long id);
    List<MateriaDTO> obtenerTodasLasMateriasConUnidades();
    MateriaDTO actualizarUnidadesDeMateria(Long materiaId, List<UnidadDTO> unidadesDTO);
}
