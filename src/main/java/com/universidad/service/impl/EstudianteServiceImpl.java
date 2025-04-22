package com.universidad.service.impl;

import com.universidad.dto.EstudianteDTO;
import com.universidad.dto.MateriaDTO;
import com.universidad.exception.ExceptionEstudiante;
import com.universidad.model.Estudiante;
import com.universidad.model.Materia;
import com.universidad.repository.EstudianteRepository;
import com.universidad.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements IEstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public List<EstudianteDTO> obtenerTodosLosEstudiantes() {
        return estudianteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EstudianteDTO obtenerEstudiantePorNumeroInscripcion(String numeroInscripcion) {
        Estudiante estudiante = estudianteRepository.findByNumeroInscripcion(numeroInscripcion);
        return convertToDTO(estudiante);
    }

    @Override
    public List<EstudianteDTO> obtenerEstudianteActivo() {
        return estudianteRepository.findAll().stream()
                .filter(estudiante -> "activo".equalsIgnoreCase(estudiante.getEstado()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Materia> obtenerMateriasDeEstudiante(Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return estudiante.getMaterias();
    }

    @Override
    public EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO) {
        Estudiante estudiante = convertToEntity(estudianteDTO);
        Estudiante estudianteGuardado = estudianteRepository.save(estudiante);
        return convertToDTO(estudianteGuardado);
    }

    @Override
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO) {
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
    public EstudianteDTO buscarPorNombre(String nombre){
        Estudiante estudiante = estudianteRepository.findByNombre(nombre);
        return convertToDTO(estudiante);
    }

    @Override
    public List<EstudianteDTO> ordenarPorApellido(){
        return estudianteRepository.findAllByOrderByApellidoAsc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
