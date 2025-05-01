package com.universidad.service;

<<<<<<< HEAD
public interface IMateriaService {
=======
import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import java.util.List;

public interface IMateriaService {
    List<MateriaDTO> obtenerTodasLasMaterias();
    MateriaDTO obtenerMateriaPorId(Long id);
    MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico);
    MateriaDTO crearMateria(MateriaDTO materia);
    MateriaDTO actualizarMateria(Long id, MateriaDTO materia);
    void eliminarMateria(Long id);
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
}
