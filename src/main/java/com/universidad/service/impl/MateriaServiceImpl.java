package com.universidad.service.impl;

import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadTematicaDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private UnidadTematicaRepository unidadTematicaRepository;

    // Mapea Materia a MateriaDTO
    private MateriaDTO mapToDTO(Materia materia) {
        if (materia == null) return null;
        return MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .prerequisitos(materia.getPrerequisitos() != null
                        ? materia.getPrerequisitos().stream()
                        .map(Materia::getId)
                        .collect(Collectors.toList())
                        : null)
                .esPrerequisitoDe(materia.getEsPrerequisitoDe() != null
                        ? materia.getEsPrerequisitoDe().stream()
                        .map(Materia::getId)
                        .collect(Collectors.toList())
                        : null)
                .unidades(materia.getUnidades() != null
                        ? materia.getUnidades().stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    // Mapea UnidadTematica a UnidadTematicaDTO
    private UnidadTematicaDTO mapToDTO(UnidadTematica unidad) {
        if (unidad == null) return null;
        return UnidadTematicaDTO.builder()
                .id(unidad.getId())
                .nombre(unidad.getNombre())
                .descripcion(unidad.getDescripcion())
                .build();
    }


    @Override
    @Cacheable(value = "materias")
    public List<MateriaDTO> obtenerTodasLasMaterias() {
        return materiaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "materia", key = "#id")
    public MateriaDTO obtenerMateriaPorId(Long id) {
        return materiaRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    @Override
    @Cacheable(value = "materia", key = "#codigoUnico")
    public MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico) {
        Materia materia = materiaRepository.findByCodigoUnico(codigoUnico);
        return mapToDTO(materia);
    }

    @Override
    @CachePut(value = "materia", key = "#result.id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
        Materia materia = new Materia();
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        Materia savedMateria = materiaRepository.save(materia);
        return mapToDTO(savedMateria);
    }

    @Override
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO actualizarMateria(Long id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Materia not found"));
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        Materia updatedMateria = materiaRepository.save(materia);
        return mapToDTO(updatedMateria);
    }

    @Override
    @CacheEvict(value = {"materia", "materias"}, allEntries = true)
    public void eliminarMateria(Long id) {
        materiaRepository.deleteById(id);
    }

    @Override
    public List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId) {
        List<UnidadTematica> unidades = unidadTematicaRepository.findByMateriaId(materiaId);
        return unidades.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UnidadTematicaDTO> reemplazarUnidadesDeMateria(Long materiaId, List<UnidadTematicaDTO> nuevasUnidades) {
        // 1) Cargar la materia (o lanzar excepción si no existe)
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("Materia no encontrada"));

// 2) Validación extra: asegurar que no haya nombres de unidad duplicados
        Set<String> nombres = nuevasUnidades.stream()
                .map(UnidadTematicaDTO::getNombre)
                .collect(Collectors.toSet());
        if (nombres.size() < nuevasUnidades.size()) {
            throw new IllegalArgumentException("Hay unidades con nombres duplicados");
        }

List<UnidadTematica> actuales = materia.getUnidades();
        actuales.clear();

// 2) Convierto y agrego cada nueva unidad a la *misma* lista
        for (UnidadTematicaDTO dto : nuevasUnidades) {
            UnidadTematica u = new UnidadTematica();
            u.setNombre(dto.getNombre());
            u.setDescripcion(dto.getDescripcion());
            u.setMateria(materia);
            actuales.add(u);
        }

// 3) Guardo: Hibernate detecta las huérfanas y las elimina, y persiste las nuevas
        Materia saved = materiaRepository.save(materia);

// 6) Mapear a DTO y devolver
        return saved.getUnidades().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }


}
