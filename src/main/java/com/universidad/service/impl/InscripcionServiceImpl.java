package com.universidad.service.impl;

import com.universidad.model.Inscripcion;
import com.universidad.model.Estudiante;
import com.universidad.model.Materia;
import com.universidad.repository.InscripcionRepository;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IInscripcionService;
import com.universidad.validation.BusinessException;
import com.universidad.dto.InscripcionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements IInscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    private InscripcionDTO toDTO(Inscripcion inscripcion) {
        InscripcionDTO dto = new InscripcionDTO();
        dto.setId(inscripcion.getId());
        dto.setEstudianteId(inscripcion.getEstudiante().getId());
        dto.setMateriaId(inscripcion.getMateria().getId());
        dto.setEstudianteNombre(inscripcion.getEstudiante().getNombre());
        dto.setMateriaNombre(inscripcion.getMateria().getNombreMateria());
        dto.setFechaInscripcion(inscripcion.getFechaInscripcion());
        dto.setEstado(inscripcion.getEstado());
        return dto;
    }

    @Override
    @Transactional
    public InscripcionDTO crearInscripcion(Long estudianteId, Long materiaId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new BusinessException("Estudiante no encontrado"));
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new BusinessException("Materia no encontrada"));
        if (inscripcionRepository.findByEstudianteIdAndMateriaId(estudianteId, materiaId).isPresent()) {
            throw new BusinessException("El estudiante ya está inscrito en esta materia");
        }
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setMateria(materia);
        inscripcion.setFechaInscripcion(java.time.LocalDate.now());
        inscripcion.setEstado("ACTIVA");
        return toDTO(inscripcionRepository.save(inscripcion));
    }

    @Override
    @Transactional
    public InscripcionDTO actualizarInscripcion(Long inscripcionId, String nuevoEstado) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new BusinessException("Inscripción no encontrada"));
        inscripcion.setEstado(nuevoEstado);
        return toDTO(inscripcionRepository.save(inscripcion));
    }

    @Override
    @Transactional
    public void eliminarInscripcion(Long inscripcionId) {
        if (!inscripcionRepository.existsById(inscripcionId)) {
            throw new BusinessException("Inscripción no encontrada");
        }
        inscripcionRepository.deleteById(inscripcionId);
    }

    @Override
    public List<InscripcionDTO> listarInscripcionesPorEstudiante(Long estudianteId) {
        return inscripcionRepository.findByEstudianteId(estudianteId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<InscripcionDTO> listarInscripcionesPorMateria(Long materiaId) {
        return inscripcionRepository.findByMateriaId(materiaId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public InscripcionDTO obtenerInscripcion(Long inscripcionId) {
        return toDTO(inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new BusinessException("Inscripción no encontrada")));
    }
}
