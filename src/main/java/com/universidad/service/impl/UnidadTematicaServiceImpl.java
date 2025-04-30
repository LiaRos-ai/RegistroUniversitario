package com.universidad.service.impl;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IUnidadTematicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {

    @Autowired
    private UnidadTematicaRepository unidadTematicaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    // Método utilitario para mapear UnidadTematica a UnidadTematicaDTO
    private UnidadTematicaDTO mapToDTO(UnidadTematica unidad) {
        if (unidad == null) return null;
        return UnidadTematicaDTO.builder()
                .id(unidad.getId())
                .titulo(unidad.getTitulo())
                .descripcion(unidad.getDescripcion())
                .orden(unidad.getOrden())
                .materiaId(unidad.getMateria() != null ? unidad.getMateria().getId() : null)
                .build();
    }

    // Método utilitario para mapear UnidadTematicaDTO a UnidadTematica
    private UnidadTematica mapToEntity(UnidadTematicaDTO dto) {
        if (dto == null) return null;
        UnidadTematica unidad = new UnidadTematica();
        unidad.setId(dto.getId());
        unidad.setTitulo(dto.getTitulo());
        unidad.setDescripcion(dto.getDescripcion());
        unidad.setOrden(dto.getOrden());

        if (dto.getMateriaId() != null) {
            Materia materia = materiaRepository.findById(dto.getMateriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Materia not found"));
            unidad.setMateria(materia);
        }

        return unidad;
    }

    @Override
    @Cacheable(value = "unidades")
    public List<UnidadTematicaDTO> obtenerTodasLasUnidades() {
        return unidadTematicaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "unidad", key = "#id")
    public UnidadTematicaDTO obtenerUnidadPorId(Long id) {
        return unidadTematicaRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    @Cacheable(value = "unidadesPorMateria", key = "#materiaId")
    public List<UnidadTematicaDTO> obtenerUnidadesPorMateriaId(Long materiaId) {
        return unidadTematicaRepository.findByMateriaIdOrderByOrdenAsc(materiaId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "unidad", key = "#result.id")
    @CacheEvict(value = {"unidades", "unidadesPorMateria"}, allEntries = true)
    public UnidadTematicaDTO crearUnidad(UnidadTematicaDTO unidadDTO) {
        UnidadTematica unidad = mapToEntity(unidadDTO);
        UnidadTematica savedUnidad = unidadTematicaRepository.save(unidad);
        return mapToDTO(savedUnidad);
    }

    @Override
    @Transactional
    @CachePut(value = "unidad", key = "#id")
    @CacheEvict(value = {"unidades", "unidadesPorMateria"}, allEntries = true)
    public UnidadTematicaDTO actualizarUnidad(Long id, UnidadTematicaDTO unidadDTO) {
        UnidadTematica unidad = unidadTematicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unidad temática not found"));

        unidad.setTitulo(unidadDTO.getTitulo());
        unidad.setDescripcion(unidadDTO.getDescripcion());
        unidad.setOrden(unidadDTO.getOrden());

        if (unidadDTO.getMateriaId() != null) {
            Materia materia = materiaRepository.findById(unidadDTO.getMateriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Materia not found"));
            unidad.setMateria(materia);
        }

        UnidadTematica updatedUnidad = unidadTematicaRepository.save(unidad);
        return mapToDTO(updatedUnidad);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"unidad", "unidades", "unidadesPorMateria"}, allEntries = true)
    public void eliminarUnidad(Long id) {
        unidadTematicaRepository.deleteById(id);
    }
}
