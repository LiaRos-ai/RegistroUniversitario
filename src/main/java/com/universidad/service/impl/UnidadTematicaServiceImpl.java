package com.universidad.service.impl;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IUnidadTematicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {

    @Autowired
    private UnidadTematicaRepository unidadRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    private UnidadTematicaDTO mapToDTO(UnidadTematica unidad) {
        return UnidadTematicaDTO.builder()
                .id(unidad.getId())
                .nombre(unidad.getNombre())
                .descripcion(unidad.getDescripcion())
                .orden(unidad.getOrden())
                .materiaId(unidad.getMateria().getId())
                .build();
    }

    private UnidadTematica mapToEntity(UnidadTematicaDTO dto, Materia materia) {
        return UnidadTematica.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .orden(dto.getOrden())
                .materia(materia)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId) {
        return unidadRepository.findByMateriaId(materiaId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UnidadTematicaDTO crearUnidad(UnidadTematicaDTO unidadDTO, Long materiaId) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));

        UnidadTematica unidad = mapToEntity(unidadDTO, materia);
        unidad = unidadRepository.save(unidad);

        return mapToDTO(unidad);
    }
}
