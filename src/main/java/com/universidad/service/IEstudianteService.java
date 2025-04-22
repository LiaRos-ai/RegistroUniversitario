package com.universidad.service;

import com.universidad.dto.EstudianteDTO;
import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import java.util.List;
import java.util.Optional;

public interface IEstudianteService {
    List<EstudianteDTO> obtenerTodosLosEstudiantes();
    List<EstudianteDTO> obtenerEstudianteActivo();
    EstudianteDTO obtenerEstudiantePorNumeroInscripcion(String numeroInscripcion);
    List<Materia> obtenerMateriasDeEstudiante(Long estudianteId);
    EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO);
    EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO);
    EstudianteDTO eliminarId(Long id);
    EstudianteDTO buscarPorNombre(String nombre);
    List<EstudianteDTO> ordenarPorApellido();
    List<MateriaDTO> materiasInscritasSegunId(Long id);

}
