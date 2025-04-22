package com.universidad.service.impl; // Define el paquete al que pertenece esta clase

import com.universidad.dto.EstudianteDTO;
import com.universidad.model.Estudiante;
import com.universidad.model.Materia;
import com.universidad.repository.EstudianteRepository;
import com.universidad.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Anotación que indica que esta clase es un servicio de Spring
public class EstudianteServiceImpl implements IEstudianteService { // Define la clase EstudianteServiceImpl que
    // implementa la interfaz IEstudianteService

    @Autowired
    private EstudianteRepository estudianteRepository; // Inyección de dependencias del repositorio de estudiantes

    @Override
    public List<EstudianteDTO> obtenerTodosLosEstudiantes() {
        // Obtiene todos los estudiantes y los convierte a DTO
        return estudianteRepository.findAll().stream() // Obtiene todos los estudiantes de la base de datos
                .map(this::convertToDTO) // Convierte cada Estudiante a EstudianteDTO
                .collect(Collectors.toList()); // Recoge los resultados en una lista
    }

    @Override
    public EstudianteDTO obtenerEstudiantePorNumeroInscripcion(String numeroInscripcion) {
        // Busca un estudiante por su número de inscripción y lo convierte a DTO
        Estudiante estudiante = estudianteRepository.findByNumeroInscripcion(numeroInscripcion); // Busca el estudiante
        // por su número de
        // inscripción
        return convertToDTO(estudiante); // Convierte el Estudiante a EstudianteDTO y lo retorna
    }

    @Override
    public List<EstudianteDTO> obtenerEstudianteActivo() { // Método para obtener una lista de estudiantes activos
        // Busca todos los estudiantes activos y los convierte a DTO
        return estudianteRepository.findAll().stream() // Obtiene todos los estudiantes de la base de datos
                .filter(estudiante -> "activo".equalsIgnoreCase(estudiante.getEstado())) // Filtra los estudiantes
                // activos
                .map(this::convertToDTO) // Convierte cada Estudiante a EstudianteDTO
                .collect(Collectors.toList()); // Recoge los resultados en una lista
    }

    @Override
    public List<Materia> obtenerMateriasDeEstudiante(Long estudianteId) {
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(estudianteId);
        return estudianteOptional.map(estudiante -> {
            List<Materia> materias = estudiante.getMaterias();
            if (materias == null || materias.isEmpty()) {
                throw new RuntimeException("El estudiante no tiene materias asignadas");
            }
            return materias;
        }).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @Override
    public List<EstudianteDTO> obtenerEstudiantesPorCurso(String materia) {
        // Grupo 3
        List<Estudiante> estudiantes = estudianteRepository.findAll(); // Busca la materia por su nombre
        return (estudiantes.stream() // Convierte la lista de estudiantes a un stream
                .filter(estudiante -> estudiante.getMaterias().stream() // Filtra los estudiantes que tienen la materia
                        .anyMatch(m -> m.getCodigoUnico().equalsIgnoreCase(materia))) // Verifica si la materia está en
                // la lista de materias del
                // estudiante
                .map(this::convertToDTO).collect(Collectors.toList())); // Recoge los resultados en una lista)
    }

    @Override
    public EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO) { // Método para crear un nuevo estudiante
        // Convierte el DTO a entidad, guarda el estudiante y lo convierte de nuevo a
        // DTO
        Estudiante estudiante = convertToEntity(estudianteDTO); // Convierte el EstudianteDTO a Estudiante
        Estudiante estudianteGuardado = estudianteRepository.save(estudiante); // Guarda el estudiante en la base de
        // datos
        return convertToDTO(estudianteGuardado); // Convierte el Estudiante guardado a EstudianteDTO y lo retorna
    }

    @Override
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO) { // Método para actualizar un
        // estudiante existente
        // Busca el estudiante por su ID, actualiza sus datos y lo guarda de nuevo
        Estudiante estudianteExistente = estudianteRepository.findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado")); // Lanza una excepción si el
        // estudiante no se encuentra
        estudianteExistente.setNombre(estudianteDTO.getNombre()); // Actualiza el nombre
        estudianteExistente.setApellido(estudianteDTO.getApellido()); // Actualiza el apellido
        estudianteExistente.setEmail(estudianteDTO.getEmail()); // Actualiza el email
        estudianteExistente.setFechaNacimiento(estudianteDTO.getFechaNacimiento()); // Actualiza la fecha de nacimiento
        estudianteExistente.setNumeroInscripcion(estudianteDTO.getNumeroInscripcion()); // Actualiza el número de
        // inscripción
        estudianteExistente.setUsuarioModificacion("admin"); // Actualiza el usuario de modificación
        estudianteExistente.setFechaModificacion(LocalDate.now()); // Actualiza la fecha de modificación

        Estudiante estudianteActualizado = estudianteRepository.save(estudianteExistente); // Guarda el estudiante
        // actualizado en la base de
        // datos
        return convertToDTO(estudianteActualizado); // Convierte el Estudiante actualizado a EstudianteDTO y lo retorna
    }

    @Override
    public EstudianteDTO eliminarEstudiante(Long id, EstudianteDTO estudianteDTO) { // Método para eliminar (de manera
        // lógica) un estudiante por su ID
        Estudiante estudianteExistente = estudianteRepository.findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado")); // Lanza una excepción si el
        // estudiante no se encuentra
        estudianteExistente.setEstado("inactivo"); // Actualiza el estado a inactivo
        estudianteExistente.setUsuarioBaja("admin"); // Asigna el usuario que dio de baja al estudiante
        estudianteExistente.setFechaBaja(LocalDate.now()); // Actualiza la fecha de baja
        estudianteExistente.setMotivoBaja(estudianteDTO.getMotivoBaja()); // Actualiza el motivo de baja

        Estudiante estudianteInactivo = estudianteRepository.save(estudianteExistente); // Guarda el estudiante inactivo
        // en la base de datos
        return convertToDTO(estudianteInactivo); // Convierte el Estudiante inactivo a EstudianteDTO y lo retorna
    }

    // Método auxiliar para convertir entidad a DTO
    private EstudianteDTO convertToDTO(Estudiante estudiante) { // Método para convertir un Estudiante a EstudianteDTO
        return EstudianteDTO.builder() // Usa el patrón builder para crear un EstudianteDTO
                .id(estudiante.getId()) // Asigna el ID
                .nombre(estudiante.getNombre()) // Asigna el nombre
                .apellido(estudiante.getApellido()) // Asigna el apellido
                .email(estudiante.getEmail()) // Asigna el email
                .fechaNacimiento(estudiante.getFechaNacimiento()) // Asigna la fecha de nacimiento
                .numeroInscripcion(estudiante.getNumeroInscripcion()) // Asigna el número de inscripción
                .estado(estudiante.getEstado()) // Asigna el estado (puede ser null si no se desea mostrar)
                .usuarioAlta(estudiante.getUsuarioAlta()) // Asigna el usuario de alta
                .fechaAlta(estudiante.getFechaAlta()) // Asigna la fecha de alta (puede ser null si no se desea mostrar)
                .usuarioModificacion(estudiante.getUsuarioModificacion()) // Asigna el usuario de modificación
                .usuarioBaja(estudiante.getUsuarioBaja()) // Asigna el usuario de baja (puede ser null si no se desea
                // mostrar)
                .fechaBaja(estudiante.getFechaBaja()) // Asigna la fecha de baja (puede ser null si no se desea mostrar)
                .motivoBaja(estudiante.getMotivoBaja()) // Asigna el motivo de baja (puede ser null si no se desea
                // mostrar)
                .build(); // Construye el objeto EstudianteDTO
    }

    // Método auxiliar para convertir DTO a entidad
    private Estudiante convertToEntity(EstudianteDTO estudianteDTO) { // Método para convertir un EstudianteDTO a
        // Estudiante
        return Estudiante.builder() // Usa el patrón builder para crear un Estudiante
                .id(estudianteDTO.getId()) // Asigna el ID
                .nombre(estudianteDTO.getNombre()) // Asigna el nombre
                .apellido(estudianteDTO.getApellido()) // Asigna el apellido
                .email(estudianteDTO.getEmail()) // Asigna el email
                .fechaNacimiento(estudianteDTO.getFechaNacimiento()) // Asigna la fecha de nacimiento
                .numeroInscripcion(estudianteDTO.getNumeroInscripcion()) // Asigna el número de inscripción
                .usuarioAlta(estudianteDTO.getUsuarioAlta()) // Asigna el usuario de alta
                .fechaAlta(estudianteDTO.getFechaAlta()) // Asigna la fecha de alta
                .usuarioModificacion(estudianteDTO.getUsuarioModificacion()) // Asigna el usuario de modificación
                .fechaModificacion(estudianteDTO.getFechaModificacion()) // Asigna la fecha de modificación
                .estado(estudianteDTO.getEstado()) // Asigna el estado (puede ser null si no se desea mostrar)
                .usuarioBaja(estudianteDTO.getUsuarioBaja()) // Asigna el usuario de baja (puede ser null si no se desea
                // mostrar)
                .fechaBaja(estudianteDTO.getFechaBaja()) // Asigna la fecha de baja (puede ser null si no se desea
                // mostrar)
                .motivoBaja(estudianteDTO.getMotivoBaja()) // Asigna el motivo de baja (puede ser null si no se desea
                // mostrar)
                .build(); // Construye el objeto Estudiante
    }
}