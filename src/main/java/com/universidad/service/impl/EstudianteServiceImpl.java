package com.universidad.service.impl; // Define el paquete al que pertenece esta clase

import com.universidad.dto.EstudianteDTO; // Importa la clase EstudianteDTO del paquete dto
import com.universidad.model.Estudiante; // Importa la clase Estudiante del paquete model
import com.universidad.repository.EstudianteRepository; // Importa la clase EstudianteRepository del paquete repository
import com.universidad.service.IEstudianteService; // Importa la interfaz IEstudianteService del paquete service

import jakarta.annotation.PostConstruct; // Importa la anotación PostConstruct de Jakarta
import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación Autowired de Spring
import org.springframework.stereotype.Service; // Importa la anotación Service de Spring

import java.util.ArrayList; // Importa la clase ArrayList para manejar listas
import java.util.List; // Importa la interfaz List para manejar listas

@Service // Anotación que indica que esta clase es un servicio de Spring
public class EstudianteServiceImpl implements IEstudianteService { // Define la clase EstudianteServiceImpl que implementa la interfaz IEstudianteService

    private final EstudianteRepository estudianteRepository; // Declara una variable final para el repositorio de estudiantes

    @Autowired // Anotación que indica que el constructor debe ser usado para inyección de dependencias
    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) { // Constructor que recibe el repositorio de estudiantes
        this.estudianteRepository = estudianteRepository; // Asigna el repositorio de estudiantes a la variable de instancia
    }
    
    @PostConstruct // Anotación que indica que este método debe ejecutarse después de la construcción del bean
    public void init() { // Método para inicializar datos de ejemplo
        estudianteRepository.init(); // Llama al método init del repositorio de estudiantes
    }

    @Override // Anotación que indica que este método sobrescribe un método de la interfaz
    public List<EstudianteDTO> obtenerTodosLosEstudiantes() { // Método para obtener una lista de todos los EstudianteDTO
        List<Estudiante> estudiantes = estudianteRepository.findAll(); // Obtiene todos los estudiantes del repositorio
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>(); // Crea una nueva lista para los EstudianteDTO
        
        for (Estudiante estudiante : estudiantes) { // Itera sobre la lista de estudiantes
            estudiantesDTO.add(convertToDTO(estudiante)); // Convierte cada estudiante a EstudianteDTO y lo agrega a la lista
        }
        return estudiantesDTO; // Retorna la lista de EstudianteDTO
    }

    @Override
    public EstudianteDTO obtenerEstudiantePorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con el ID: " + id));
        
        return convertToDTO(estudiante);
    }

    @Override
    public EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO) { // Método para crear un nuevo estudiante
        Estudiante estudiante = convertToEntity(estudianteDTO); // Convierte el EstudianteDTO a Estudiante
        Estudiante nuevoEstudiante = estudianteRepository.save(estudiante); // Guarda el nuevo estudiante en el repositorio
        return convertToDTO(nuevoEstudiante); // Retorna el nuevo estudiante como EstudianteDTO
    }

    @Override
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO) { // Método para actualizar un estudiante existente
        obtenerEstudiantePorId(id); // Verifica si el estudiante existe
        // Si no existe, lanzará una excepción
        // Si existe, continúa con la actualización

        Estudiante estudianteActualizado = convertToEntity(estudianteDTO); // Convierte el EstudianteDTO a Estudiante
        estudianteActualizado.setId(id); // Asigna el ID al estudiante actualizado
        Estudiante estudianteGuardado = estudianteRepository.update(id, estudianteActualizado); // Actualiza el estudiante en el repositorio
        
        return convertToDTO(estudianteGuardado); // Retorna el estudiante actualizado como EstudianteDTO
    }

    @Override
    public void eliminarEstudiante(Long id) { // Método para eliminar un estudiante por su ID
        obtenerEstudiantePorId(id); // Verifica si el estudiante existe
        // Si no existe, lanzará una excepción
        // Si existe, continúa con la eliminación

        estudianteRepository.deleteById(id); // Elimina el estudiante del repositorio
    }
    // Método para inicializar algunos datos de ejemplo

    // Método auxiliar para convertir entidad a DTO
    private EstudianteDTO convertToDTO(Estudiante estudiante) { // Método para convertir un Estudiante a EstudianteDTO
        return EstudianteDTO.builder() // Usa el patrón builder para crear un EstudianteDTO
                .id(estudiante.getId()) // Asigna el ID
                .nombre(estudiante.getNombre()) // Asigna el nombre
                .apellido(estudiante.getApellido()) // Asigna el apellido
                .email(estudiante.getEmail()) // Asigna el email
                .fechaNacimiento(estudiante.getFechaNacimiento()) // Asigna la fecha de nacimiento
                .numeroInscripcion(estudiante.getNumeroInscripcion()) // Asigna el número de inscripción
                .build(); // Construye el objeto EstudianteDTO
    }
    
    // Método auxiliar para convertir DTO a entidad
    private Estudiante convertToEntity(EstudianteDTO estudianteDTO) { // Método para convertir un EstudianteDTO a Estudiante
        return Estudiante.builder() // Usa el patrón builder para crear un Estudiante
                .id(estudianteDTO.getId()) // Asigna el ID
                .nombre(estudianteDTO.getNombre()) // Asigna el nombre
                .apellido(estudianteDTO.getApellido()) // Asigna el apellido
                .email(estudianteDTO.getEmail()) // Asigna el email
                .fechaNacimiento(estudianteDTO.getFechaNacimiento()) // Asigna la fecha de nacimiento
                .numeroInscripcion(estudianteDTO.getNumeroInscripcion()) // Asigna el número de inscripción
                .build(); // Construye el objeto Estudiante
    }
}