package com.universidad.service;

import com.universidad.dto.DocenteDTO;
import java.util.List;

public interface IDocenteService {
    void asignarMateria(Long docenteId, Long materiaId);
    void desasignarMateria(Long docenteId, Long materiaId);
    DocenteDTO obtenerDocentePorId(Long docenteId);
    DocenteDTO guardarDocente(DocenteDTO docente);
    DocenteDTO actualizarDocente(Long docenteId, DocenteDTO docente);
    void eliminarDocente(Long docenteId);
    List<DocenteDTO> listarDocentes();
}
