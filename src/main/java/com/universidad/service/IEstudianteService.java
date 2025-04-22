package com.universidad.service; // Define el paquete al que pertenece esta interfaz

import com.universidad.dto.EstudianteDTO; // Importa la clase EstudianteDTO del paquete dto
import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;

import java.util.List; // Importa la interfaz List para manejar listas
import java.util.Optional;

public interface IEstudianteService { // Define la interfaz IEstudianteService

    /**
     * Obtiene todos los estudiantes.
     * 
     * @return Lista de EstudianteDTO.
     */
    List<EstudianteDTO> obtenerTodosLosEstudiantes(); // Método para obtener una lista de todos los EstudianteDTO

    /**
     * Obtiene un estudiante activo.
     * 
     * @return Lista de EstudianteDTO activos.
     * @throws RuntimeException si no se encuentra el estudiante activo.
     */
    List<EstudianteDTO> obtenerEstudianteActivo(); // Método para obtener una lista de EstudianteDTO activos

    /**
     * Obtiene un estudiante por su número de inscripción.
     * 
     * @param numeroInscripcion
     * @return
     */
    EstudianteDTO obtenerEstudiantePorNumeroInscripcion(String numeroInscripcion); // Método para obtener un estudiante
                                                                                   // por su número de inscripción

    /**
     * Obtiene las materias de un estudiante por su ID.
     * 
     * @param estudianteId ID del estudiante.
     * @return Lista de materias del estudiante.
     */
    public List<Materia> obtenerMateriasDeEstudiante(Long estudianteId); // Método para obtener las materias de un
                                                                         // estudiante por su ID

    /**
     * Crea un nuevo estudiante.
     * 
     * @param estudianteDTO DTO del estudiante a crear.
     * @return EstudianteDTO creado.
     */
    EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO); // Método para crear un nuevo estudiante

    /**
     * Actualiza un estudiante existente.
     * 
     * @param id            ID del estudiante a actualizar.
     * @param estudianteDTO DTO del estudiante con los nuevos datos.
     * @return EstudianteDTO actualizado.
     *
     * @throws RuntimeException si el estudiante no se encuentra.
     */
    EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO); // Método para actualizar un estudiante
                                                                              // existente

    /**
     * Elimina un estudiante por su ID.
     * 
     * @param id ID del estudiante a eliminar.
     */
    EstudianteDTO eliminarEstudiante(Long id, EstudianteDTO estudianteDTO); // Método para eliminar (de manera logica)
                                                                            // un estudiante por su ID

    // --------------------- Operaciones opcionales con Optional
    // ---------------------

    /**
     * Obtiene los estudiantes inscritos en una materia (por nombre de la materia).
     * 
     * @param nombreMateria Nombre de la materia.
     * @return Lista de EstudianteDTO o vacío si no hay estudiantes.
     */
    Optional<List<EstudianteDTO>> obtenerEstudiantesPorMateria(String nombreMateria);

    /**
     * Obtiene las materias en las que está inscrito un estudiante por su ID.
     * 
     * @param idEstudiante ID del estudiante.
     * @return Lista de MateriaDTO o vacío si no hay materias.
     */
    Optional<List<MateriaDTO>> obtenerMateriasPorEstudiante(Long idEstudiante);
}