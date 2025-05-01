package com.universidad.service.impl;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.UnidadTematicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnidadTematicaServiceImpl implements UnidadTematicaService {

    private final UnidadTematicaRepository unidadRepo;
    private final MateriaRepository materiaRepo;

    @Override
    public UnidadTematicaDTO crearUnidad(UnidadTematicaDTO unidadDTO) {
        Materia materia = materiaRepo.findById(unidadDTO.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        UnidadTematica unidad = UnidadTematica.builder()
                .titulo(unidadDTO.getTitulo())
                .materia(materia)
                .build();

        unidad = unidadRepo.save(unidad);

        return UnidadTematicaDTO.builder()
                .id(unidad.getId())
                .titulo(unidad.getTitulo())
                .idMateria(unidad.getMateria().getId())
                .build();
    }

    @Override
    public List<UnidadTematicaDTO> listarPorMateria(Long idMateria) {
        return unidadRepo.findByMateriaId(idMateria).stream()
                .map(unidad -> UnidadTematicaDTO.builder()
                        .id(unidad.getId())
                        .titulo(unidad.getTitulo())
                        .idMateria(unidad.getMateria().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarUnidad(Long idUnidad) {
        unidadRepo.deleteById(idUnidad);
    }
}
