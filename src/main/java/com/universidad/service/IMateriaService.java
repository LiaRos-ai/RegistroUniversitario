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

    List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId);


    List<UnidadTematicaDTO> reemplazarUnidadesDeMateria(Long materiaId,
                                                        List<UnidadTematicaDTO> nuevasUnidades);
}
