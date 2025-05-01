package com.universidad.service.impl;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IUnidadTematicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {

    @Autowired
    private UnidadTematicaRepository unidadRepo;

    @Override
    public List<UnidadTematicaDTO> listarPorMateria(Long materiaId) {
        return unidadRepo.findByMateriaId(materiaId)
                .stream()
                .map(unidad -> UnidadTematicaDTO.builder()
                        .id(unidad.getId())
                        .id_materia(unidad.getMateria().getId().toString())
                        .nombre_unidad(unidad.getNombre_unidad())
                        .descripcion(unidad.getDescripcion())
                        .contenido(unidad.getContenido())
                        .build())
                .collect(Collectors.toList());
    }
}
