package com.universidad.service.impl;

import com.universidad.dto.EstudianteDTO;
import com.universidad.model.Estudiante;
import com.universidad.model.Materia;
import com.universidad.repository.EstudianteRepository;
import com.universidad.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<List<Materia>> obtenerMateriasDeEstudiante(Long estudianteId) {
        return estudianteRepository.findById(estudianteId)
                .map(Estudiante::getMaterias); // Devuelve Optional<List<Materia>>
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
    public void eliminarEstudiante(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        estudianteRepository.delete(estudiante);
    }

    // Método auxiliar para convertir entidad a DTO
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

    // Método auxiliar para convertir DTO a entidad
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


