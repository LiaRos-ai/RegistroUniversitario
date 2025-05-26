package com.universidad.service.impl;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;
import com.universidad.model.Materia;
import com.universidad.repository.DocenteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IDocenteService;
import com.universidad.validation.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteServiceImpl implements IDocenteService {
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    private DocenteDTO toDTO(Docente docente) {
        if (docente == null) return null;
        return DocenteDTO.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .email(docente.getEmail())
                .fechaNacimiento(docente.getFechaNacimiento())
                .nroEmpleado(docente.getNroEmpleado())
                .departamento(docente.getDepartamento())
                .build();
    }

    private Docente toEntity(DocenteDTO dto) {
        if (dto == null) return null;
        return Docente.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .nroEmpleado(dto.getNroEmpleado())
                .departamento(dto.getDepartamento())
                .build();
    }

    /**
     * Asigna una materia a un docente si no está ya asignada.
     * @param docenteId ID del docente
     * @param materiaId ID de la materia
     * @throws BusinessException si el docente o la materia no existen, o si ya está asignada
     */
    @Override
    @Transactional
    public void asignarMateria(Long docenteId, Long materiaId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new BusinessException("Docente no encontrado"));
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new BusinessException("Materia no encontrada"));
        if (docente.getMaterias() == null) {
            docente.setMaterias(new java.util.ArrayList<>());
        }
        if (docente.getMaterias().contains(materia)) {
            throw new BusinessException("La materia ya está asignada a este docente");
        }
        docente.getMaterias().add(materia);
        docenteRepository.save(docente);
    }

    /**
     * Desasigna una materia de un docente si está asignada.
     * @param docenteId ID del docente
     * @param materiaId ID de la materia
     * @throws BusinessException si el docente o la materia no existen, o si no está asignada
     */
    @Override
    @Transactional
    public void desasignarMateria(Long docenteId, Long materiaId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new BusinessException("Docente no encontrado"));
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new BusinessException("Materia no encontrada"));
        if (docente.getMaterias() == null || !docente.getMaterias().contains(materia)) {
            throw new BusinessException("La materia no está asignada a este docente");
        }
        docente.getMaterias().remove(materia);
        docenteRepository.save(docente);
    }

    /**
     * Obtiene un docente por su ID.
     * @param docenteId ID del docente
     * @return Docente encontrado
     * @throws BusinessException si el docente no existe
     */
    @Override
    public DocenteDTO obtenerDocentePorId(Long docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new BusinessException("Docente no encontrado"));
        return toDTO(docente);
    }

    @Override
    public DocenteDTO guardarDocente(DocenteDTO docenteDTO) {
        Docente docente = toEntity(docenteDTO);
        Docente saved = docenteRepository.save(docente);
        return toDTO(saved);
    }

    @Override
    public DocenteDTO actualizarDocente(Long docenteId, DocenteDTO docenteDTO) {
        Docente existente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new BusinessException("Docente no encontrado"));
        existente.setNombre(docenteDTO.getNombre());
        existente.setApellido(docenteDTO.getApellido());
        existente.setEmail(docenteDTO.getEmail());
        existente.setFechaNacimiento(docenteDTO.getFechaNacimiento());
        existente.setNroEmpleado(docenteDTO.getNroEmpleado());
        existente.setDepartamento(docenteDTO.getDepartamento());
        Docente actualizado = docenteRepository.save(existente);
        return toDTO(actualizado);
    }

    @Override
    public void eliminarDocente(Long docenteId) {
        if (!docenteRepository.existsById(docenteId)) {
            throw new BusinessException("Docente no encontrado");
        }
        docenteRepository.deleteById(docenteId);
    }

    @Override
    public List<DocenteDTO> listarDocentes() {
        return docenteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
}
