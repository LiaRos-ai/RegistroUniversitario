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

    Materia buscarMateriaPorId(Long id); // Para lógica interna o controlador
    List<Materia> listarMateriasConUnidades();
    void reemplazarUnidadesTematicas(Long idMateria, List<UnidadTematica> nuevasUnidades);
}
