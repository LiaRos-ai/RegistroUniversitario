package com.universidad.service.impl;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IUnidadTematicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {

    @Autowired
    private UnidadTematicaRepository unidadTematicaRepository;

    // Convertir UnidadTematica a UnidadTematicaDTO
    private UnidadTematicaDTO convertirADTO(UnidadTematica unidad) {
        return UnidadTematicaDTO.builder()
                .id(unidad.getId())
                .tema(unidad.getTema())
                .descripcion(unidad.getDescripcion())
                .materiaId(unidad.getMateria().getId())  // Relacionamos la unidad con la materia
                .build();
    }

    // Obtener todas las unidades temáticas
    @Override
    public List<UnidadTematicaDTO> obtenerTodasLasUnidades() {
        return unidadTematicaRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // Obtener unidades temáticas por materia (id de la materia)
    @Override
    public List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId) {
        List<UnidadTematica> unidades = unidadTematicaRepository.findByMateriaId(materiaId);
        return unidades.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // Crear una nueva unidad temática
    @Override
    public UnidadTematicaDTO crearUnidadTematica(UnidadTematicaDTO unidadTematicaDTO) {
        UnidadTematica unidad = new UnidadTematica();
        unidad.setTema(unidadTematicaDTO.getTema());
        unidad.setDescripcion(unidadTematicaDTO.getDescripcion());

        // Aquí asocias la unidad temática con la materia correspondiente
        // Por ejemplo, si tienes el ID de la materia en el DTO:
        // Materia materia = materiaRepository.findById(unidadTematicaDTO.getMateriaId())
        // unidad.setMateria(materia);

        UnidadTematica savedUnidad = unidadTematicaRepository.save(unidad);
        return convertirADTO(savedUnidad);
    }

    // Actualizar una unidad temática
    @Override
    public UnidadTematicaDTO actualizarUnidadTematica(Long id, UnidadTematicaDTO unidadTematicaDTO) {
        UnidadTematica unidad = unidadTematicaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Unidad temática no encontrada"));
        unidad.setTema(unidadTematicaDTO.getTema());
        unidad.setDescripcion(unidadTematicaDTO.getDescripcion());

        UnidadTematica updatedUnidad = unidadTematicaRepository.save(unidad);
        return convertirADTO(updatedUnidad);
    }

    // Eliminar una unidad temática
    @Override
    public void eliminarUnidadTematica(Long id) {
        unidadTematicaRepository.deleteById(id);
    }
}
