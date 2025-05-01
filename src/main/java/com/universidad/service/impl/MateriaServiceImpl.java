package com.universidad.service.impl;

import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;
    
    @Autowired
    private UnidadTematicaRepository unidadTematicaRepository;

    @Override
    @Cacheable(value = "materias")
    @Transactional(readOnly = true)
    public List<MateriaDTO> obtenerTodasLasMaterias() {
        return materiaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "materia", key = "#id")
    public MateriaDTO obtenerMateriaPorId(Long id) {
        return materiaRepository.findByIdWithUnidades(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    // Método mejorado para mapear Materia a MateriaDTO
    private MateriaDTO mapToDTO(Materia materia) {
        if (materia == null) return null;
        
        // Cargar unidades explícitamente si es necesario
        if(materia.getUnidadesTematicas() != null && !materia.getUnidadesTematicas().isEmpty()) {
            materia.getUnidadesTematicas().size(); // Esto fuerza la carga si es Lazy
        }
        
        return MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .prerequisitos(materia.getPrerequisitos() != null ?
                    materia.getPrerequisitos().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                .esPrerequisitoDe(materia.getEsPrerequisitoDe() != null ?
                    materia.getEsPrerequisitoDe().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                .unidades(materia.getUnidadesTematicas() != null ?
                    materia.getUnidadesTematicas().stream()
                        .map(this::mapUnidadToDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    private UnidadDTO mapUnidadToDTO(UnidadTematica unidad) {
        if (unidad == null) return null;
        
        return UnidadDTO.builder()
                .id(unidad.getId())
                .nombre(unidad.getNombre())
                .descripcion(unidad.getDescripcion())
                .duracionHoras(unidad.getDuracionHoras())
                .materiaId(unidad.getMateria() != null ? unidad.getMateria().getId() : null)
                .build();
    }


    // Método específico para cargar con unidades
    @Transactional(readOnly = true)
    private MateriaDTO mapToDTOWithUnidades(Materia materia) {
        MateriaDTO dto = mapToDTO(materia);
        // Cargar unidades explícitamente
        List<UnidadTematica> unidades = unidadTematicaRepository.findByMateriaId(materia.getId());
        dto.setUnidades(unidades.stream()
                .map(this::mapUnidadToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    @Cacheable(value = "materia", key = "#codigoUnico")
    @Transactional(readOnly = true)
    public MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico) {
        Materia materia = materiaRepository.findByCodigoUnico(codigoUnico);
        return mapToDTOWithUnidades(materia);
    }

    @Override
    @CachePut(value = "materia", key = "#result.id")
    @CacheEvict(value = "materias", allEntries = true)
    @Transactional
    public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
        Materia materia = new Materia();
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        
        Materia savedMateria = materiaRepository.save(materia);
        
        // Si vienen unidades en el DTO, las guardamos
        if(materiaDTO.getUnidades() != null && !materiaDTO.getUnidades().isEmpty()) {
            List<UnidadTematica> unidades = materiaDTO.getUnidades().stream()
                    .map(unidadDTO -> {
                        UnidadTematica unidad = new UnidadTematica();
                        unidad.setNombre(unidadDTO.getNombre());
                        unidad.setDescripcion(unidadDTO.getDescripcion());
                        unidad.setDuracionHoras(unidadDTO.getDuracionHoras());
                        unidad.setMateria(savedMateria);
                        return unidad;
                    })
                    .collect(Collectors.toList());
            
            unidadTematicaRepository.saveAll(unidades);
        }
        
        return mapToDTOWithUnidades(savedMateria);
    }

    @Override
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    @Transactional
    public MateriaDTO actualizarMateria(Long id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));
        
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        
        Materia updatedMateria = materiaRepository.save(materia);
        return mapToDTOWithUnidades(updatedMateria);
    }

    @Override
    @CacheEvict(value = {"materia", "materias"}, allEntries = true)
    @Transactional
    public void eliminarMateria(Long id) {
        // Primero eliminamos las unidades asociadas
        unidadTematicaRepository.deleteByMateriaId(id);
        // Luego eliminamos la materia
        materiaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MateriaDTO> obtenerTodasLasMateriasConUnidades() {
        return materiaRepository.findAll().stream()
                .map(this::mapToDTOWithUnidades)
                .collect(Collectors.toList());
    }

    @Override
@Transactional
@CachePut(value = "materia", key = "#materiaId")
public MateriaDTO actualizarUnidadesDeMateria(Long materiaId, List<UnidadDTO> unidadesDTO) {
    Materia materia = materiaRepository.findById(materiaId)
            .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));
    
    // Validar duplicados (Extra)
    validarUnidadesDuplicadas(unidadesDTO);
    
    // Eliminar todas las unidades existentes (orphanRemoval se encargará de esto)
    materia.getUnidadesTematicas().clear();
    
    // Agregar las nuevas unidades
    unidadesDTO.forEach(unidadDTO -> {
        UnidadTematica unidad = new UnidadTematica();
        unidad.setNombre(unidadDTO.getNombre());
        unidad.setDescripcion(unidadDTO.getDescripcion());
        unidad.setDuracionHoras(unidadDTO.getDuracionHoras());
        unidad.setMateria(materia);
        materia.getUnidadesTematicas().add(unidad);
    });
    
    Materia materiaGuardada = materiaRepository.save(materia);
    return mapToDTO(materiaGuardada);
}

// Método para validar duplicados (Extra)
private void validarUnidadesDuplicadas(List<UnidadDTO> unidadesDTO) {
    Set<String> nombresUnidades = new HashSet<>();
    
    for (UnidadDTO unidadDTO : unidadesDTO) {
        if (!nombresUnidades.add(unidadDTO.getNombre())) {
            throw new IllegalArgumentException("No se permiten unidades con nombres duplicados: " + unidadDTO.getNombre());
        }
    }
}
}