package com.universidad.service;

import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import com.universidad.dto.UnidadTematicaDTO;
import java.util.List;

public interface IMateriaService {
    List<MateriaDTO> obtenerTodasLasMaterias();
    MateriaDTO obtenerMateriaPorId(Long id);
    MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico);
    MateriaDTO crearMateria(MateriaDTO materia);
    MateriaDTO actualizarMateria(Long id, MateriaDTO materia);
    void eliminarMateria(Long id);

    // TAREA GRUPO
    List<MateriaDTO> listarMateriasConUnidades();  // Extra: Listar materias con unidades incluidas

    // Parte 2
    MateriaDTO reemplazarUnidadesTematicas(Long id, List<UnidadTematicaDTO> nuevasUnidades);
}











