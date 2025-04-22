package com.universidad.service;

import com.universidad.dto.EstudianteDTO;
import com.universidad.model.Materia;

import java.util.List;
import java.util.Optional;

public interface IEstudianteService {

    /**
     * Obtiene todos los estudiantes.
     * @return Lista de EstudianteDTO.
     */
    List<EstudianteDTO> obtenerTodosLosEstudiantes();

    /**
     * Obtiene estudiantes activos.
     * @return Lista de EstudianteDTO activos.
     * @throws RuntimeException si no se encuentran estudiantes activos.
     */
    List<EstudianteDTO> obtenerEstudianteActivo();

    /**
     * Obtiene un estudiante por su número de inscripción.
     * @param numeroInscripcion Número de inscripción del estudiante.
     * @return EstudianteDTO correspondiente.
     */
    EstudianteDTO obtenerEstudiantePorNumeroInscripcion(String numeroInscripcion);

    /**
     * Obtiene las materias de un estudiante por su ID (devuelve Optional).
     * @param estudianteId ID del estudiante.
     * @return Optional con lista de materias del estudiante.
     */
    Optional<List<Materia>> obtenerMateriasDeEstudiante(Long estudianteId);

    /**
     * Crea un nuevo estudiante.
     * @param estudianteDTO DTO del estudiante a crear.
     * @return EstudianteDTO creado.
     */
    EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO);

    /**
     * Actualiza un estudiante existente.
     * @param id ID del estudiante a actualizar.
     * @param estudianteDTO DTO del estudiante con los nuevos datos.
     * @return EstudianteDTO actualizado.
     * @throws RuntimeException si el estudiante no se encuentra.
     */
    EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO);

    /**
     * Elimina un estudiante por su ID.
     * @param id ID del estudiante a eliminar.
     */
    void eliminarEstudiante(Long id);
}



