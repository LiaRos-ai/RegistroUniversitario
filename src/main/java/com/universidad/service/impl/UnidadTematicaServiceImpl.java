package com.universidad.service.impl;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IUnidadTematicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Unidad Temática que maneja la lógica de negocio
 * para operaciones relacionadas con unidades temáticas.
 */
@Service
@RequiredArgsConstructor
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {

    private final UnidadTematicaRepository repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UnidadTematicaDTO> obtenerPorMateria(Long materiaId) {
        return repo.findByMateriaId(materiaId)
                .stream()
                .map(unidad -> UnidadTematicaDTO.builder()
                        .id(unidad.getId())
                        .titulo(unidad.getTitulo())
                        .descripcion(unidad.getDescripcion())
                        .build())
                .collect(Collectors.toList());
    }
 // TAREA GRUPO
}
