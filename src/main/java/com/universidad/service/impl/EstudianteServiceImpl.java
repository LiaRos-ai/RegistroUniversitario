package com.universidad.service.impl;

import com.universidad.dto.EstudianteDTO;
import com.universidad.dto.MateriaDTO;
import com.universidad.exception.ExceptionEstudiante;
import com.universidad.model.Estudiante;
import com.universidad.model.Materia;
<<<<<<< HEAD
import com.universidad.repository.EstudianteRepository;
import com.universidad.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
=======
import com.universidad.repository.EstudianteRepository; // Importa la clase EstudianteRepository del paquete repository
import com.universidad.service.IEstudianteService; // Importa la interfaz IEstudianteService del paquete service
import com.universidad.validation.EstudianteValidator; // Importa la clase EstudianteValidator del paquete validation

import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación Autowired de Spring
import org.springframework.stereotype.Service; // Importa la anotación Service de Spring
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List; // Importa la interfaz List para manejar listas
import java.util.stream.Collectors; // Importa la clase Collectors para manejar colecciones
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3

@Service
public class EstudianteServiceImpl implements IEstudianteService {

    @Autowired
<<<<<<< HEAD
    private EstudianteRepository estudianteRepository;
=======
    private EstudianteRepository estudianteRepository; // Inyección de dependencias del repositorio de estudiantes

    @Autowired // Inyección de dependencias del validador de estudiantes
    private EstudianteValidator estudianteValidator; // Declara una variable para el validador de estudiantes
    
    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, EstudianteValidator estudianteValidator) {
        this.estudianteRepository = estudianteRepository;
        this.estudianteValidator = estudianteValidator;
    }

    /*public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
            this.estudianteRepository = estudianteRepository;
    }*/
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3

    @Override
    @Cacheable(value = "estudiantes")
    public List<EstudianteDTO> obtenerTodosLosEstudiantes() {
        return estudianteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "estudiante", key = "#numeroInscripcion")
    public EstudianteDTO obtenerEstudiantePorNumeroInscripcion(String numeroInscripcion) {
        Estudiante estudiante = estudianteRepository.findByNumeroInscripcion(numeroInscripcion);
        return convertToDTO(estudiante);
    }

    @Override
<<<<<<< HEAD
    public List<EstudianteDTO> obtenerEstudianteActivo() {
        return estudianteRepository.findAll().stream()
                .filter(estudiante -> "activo".equalsIgnoreCase(estudiante.getEstado()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
=======
    @Cacheable(value = "estudiantesActivos")
    public List<EstudianteDTO> obtenerEstudianteActivo() { // Método para obtener una lista de estudiantes activos
        // Busca todos los estudiantes activos y los convierte a DTO
        return estudianteRepository.findAll().stream() // Obtiene todos los estudiantes de la base de datos
                .filter(estudiante -> "activo".equalsIgnoreCase(estudiante.getEstado())) // Filtra los estudiantes activos
                .map(this::convertToDTO) // Convierte cada Estudiante a EstudianteDTO
                .collect(Collectors.toList()); // Recoge los resultados en una lista
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
    }

    @Override
<<<<<<< HEAD
    public List<Materia> obtenerMateriasDeEstudiante(Long estudianteId) {
=======
    @Cacheable(value = "materiasEstudiante", key = "#estudianteId")
    public List<Materia> obtenerMateriasDeEstudiante(Long estudianteId) { // Método para obtener las materias de un estudiante por su ID
        // Busca el estudiante por su ID y obtiene sus materias
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return estudiante.getMaterias();
    }

    @Override
<<<<<<< HEAD
    public EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO) {
        Estudiante estudiante = convertToEntity(estudianteDTO);
        Estudiante estudianteGuardado = estudianteRepository.save(estudiante);
        return convertToDTO(estudianteGuardado);
    }

    @Override
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO) {
=======
    @CachePut(value = "estudiante", key = "#result.numeroInscripcion")
    @CacheEvict(value = {"estudiantes", "estudiantesActivos"}, allEntries = true)
    public EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO) { // Método para crear un nuevo estudiante
        
        estudianteValidator.validacionCompletaEstudiante(estudianteDTO); // Valida el estudiante usando el validador

        // Convierte el DTO a entidad, guarda el estudiante y lo convierte de nuevo a DTO
        Estudiante estudiante = convertToEntity(estudianteDTO); // Convierte el EstudianteDTO a Estudiante
        Estudiante estudianteGuardado = estudianteRepository.save(estudiante); // Guarda el estudiante en la base de datos
        return convertToDTO(estudianteGuardado); // Convierte el Estudiante guardado a EstudianteDTO y lo retorna
    }

    @Override
    @CachePut(value = "estudiante", key = "#id")
    @CacheEvict(value = {"estudiantes", "estudiantesActivos"}, allEntries = true)
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO) { // Método para actualizar un estudiante existente
        // Busca el estudiante por su ID, actualiza sus datos y lo guarda de nuevo
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        estudianteExistente.setNombre(estudianteDTO.getNombre());
        estudianteExistente.setApellido(estudianteDTO.getApellido());
        estudianteExistente.setEmail(estudianteDTO.getEmail());
        estudianteExistente.setFechaNacimiento(estudianteDTO.getFechaNacimiento());
        estudianteExistente.setNumeroInscripcion(estudianteDTO.getNumeroInscripcion());
        estudianteExistente.setEstado(estudianteDTO.getEstado());
        estudianteExistente.setUsuarioAlta(estudianteDTO.getUsuarioAlta());
        estudianteExistente.setFechaAlta(estudianteDTO.getFechaAlta());
        estudianteExistente.setUsuarioModificacion(estudianteDTO.getUsuarioModificacion());
        estudianteExistente.setFechaModificacion(estudianteDTO.getFechaModificacion());
        estudianteExistente.setUsuarioBaja(estudianteDTO.getUsuarioBaja());
        estudianteExistente.setFechaBaja(estudianteDTO.getFechaBaja());
        estudianteExistente.setMotivoBaja(estudianteDTO.getMotivoBaja());

        Estudiante estudianteActualizado = estudianteRepository.save(estudianteExistente);
        return convertToDTO(estudianteActualizado);
    }
    @Override
    public EstudianteDTO eliminarId(Long id) {
        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Establecer estado a "inactivo"
        estudianteExistente.setEstado("inactivo");

        // Asignar fechas y usuarios
        estudianteExistente.setUsuarioAlta(estudianteExistente.getUsuarioAlta()); // Puedes ajustar según tu lógica
        estudianteExistente.setFechaAlta(estudianteExistente.getFechaAlta()); // Si deseas mantener la fecha de alta previa

        // Establecer las fechas de modificación y baja con la fecha actual
        estudianteExistente.setUsuarioModificacion(estudianteExistente.getUsuarioModificacion());
        estudianteExistente.setFechaModificacion(LocalDate.now());  // Fecha de modificación es la fecha actual
        estudianteExistente.setUsuarioBaja(estudianteExistente.getUsuarioBaja());
        estudianteExistente.setFechaBaja(LocalDate.now());  // Fecha de baja es la fecha actual
        estudianteExistente.setMotivoBaja("la lic lo dijo");

        // Guardar los cambios en el repositorio
        Estudiante estudianteActualizado = estudianteRepository.save(estudianteExistente);

        return convertToDTO(estudianteActualizado);
    }

    @Override
<<<<<<< HEAD
    public EstudianteDTO buscarPorNombre(String nombre){
        Estudiante estudiante = estudianteRepository.findByNombre(nombre);
        return convertToDTO(estudiante);
    }

    @Override
    public List<EstudianteDTO> ordenarPorApellido(){
        return estudianteRepository.findAllByOrderByApellidoAsc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
=======
    @CacheEvict(value = {"estudiante", "estudiantes", "estudiantesActivos"}, allEntries = true)
    public EstudianteDTO eliminarEstudiante(Long id, EstudianteDTO estudianteDTO) { // Método para eliminar (de manera lógica) un estudiante por su ID
        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado")); // Lanza una excepción si el estudiante no se encuentra
        estudianteExistente.setEstado("inactivo"); // Actualiza el estado a inactivo
        estudianteExistente.setUsuarioBaja("admin"); // Asigna el usuario que dio de baja al estudiante
        estudianteExistente.setFechaBaja(LocalDate.now()); // Actualiza la fecha de baja
        estudianteExistente.setMotivoBaja(estudianteDTO.getMotivoBaja()); // Actualiza el motivo de baja

        Estudiante estudianteInactivo = estudianteRepository.save(estudianteExistente); // Guarda el estudiante inactivo en la base de datos
        return convertToDTO(estudianteInactivo); // Convierte el Estudiante inactivo a EstudianteDTO y lo retorna
    }

    @Transactional
    public Estudiante obtenerEstudianteConBloqueo(Long id) {
        Estudiante est = estudianteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        // Simula un tiempo de procesamiento prolongado
        // Esto es solo para demostrar el bloqueo, en un caso real no se debería hacer esto
            try { Thread.sleep(15000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        // Simula un tiempo de procesamiento prolongado
        return est;
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
                .usuarioBaja(estudiante.getUsuarioBaja()) // Asigna el usuario de baja (puede ser null si no se desea mostrar)
                .fechaBaja(estudiante.getFechaBaja()) // Asigna la fecha de baja (puede ser null si no se desea mostrar)
                .motivoBaja(estudiante.getMotivoBaja()) // Asigna el motivo de baja (puede ser null si no se desea mostrar)
                .build(); // Construye el objeto EstudianteDTO
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
    }

    @Override
    public List<MateriaDTO> materiasInscritasSegunId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ExceptionEstudiante("Estudiante con ID " + id + " no encontrado"));

        return estudiante.getMaterias().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private MateriaDTO convertToDTO(Materia materia){
        return MateriaDTO.builder()
                .id(materia.getId())
                .codigoUnico(materia.getCodigoUnico())
                .nombreMateria(materia.getNombreMateria())
                .creditos(materia.getCreditos())
                .build();

    }

    private EstudianteDTO convertToDTO(Estudiante estudiante) {
        return EstudianteDTO.builder()
                .id(estudiante.getId())
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .email(estudiante.getEmail())
                .fechaNacimiento(estudiante.getFechaNacimiento())
                .numeroInscripcion(estudiante.getNumeroInscripcion())
                .estado(estudiante.getEstado())
                .usuarioAlta(estudiante.getUsuarioAlta())
                .fechaAlta(estudiante.getFechaAlta())
                .usuarioModificacion(estudiante.getUsuarioModificacion())
                .usuarioBaja(estudiante.getUsuarioBaja())
                .fechaBaja(estudiante.getFechaBaja())
                .motivoBaja(estudiante.getMotivoBaja())
                .build();
    }

    private Estudiante convertToEntity(EstudianteDTO estudianteDTO) {
        return Estudiante.builder()
                .id(estudianteDTO.getId())
                .nombre(estudianteDTO.getNombre())
                .apellido(estudianteDTO.getApellido())
                .email(estudianteDTO.getEmail())
                .fechaNacimiento(estudianteDTO.getFechaNacimiento())
                .numeroInscripcion(estudianteDTO.getNumeroInscripcion())
                .usuarioAlta(estudianteDTO.getUsuarioAlta())
                .fechaAlta(estudianteDTO.getFechaAlta())
                .usuarioModificacion(estudianteDTO.getUsuarioModificacion())
                .fechaModificacion(estudianteDTO.getFechaModificacion())
                .estado(estudianteDTO.getEstado())
                .usuarioBaja(estudianteDTO.getUsuarioBaja())
                .fechaBaja(estudianteDTO.getFechaBaja())
                .motivoBaja(estudianteDTO.getMotivoBaja())
                .build();
    }
}
