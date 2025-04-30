package com.universidad.service.impl;

import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadTematicaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    // Método utilitario para mapear Materia a MateriaDTO
    private MateriaDTO mapToDTO(Materia materia) {
        if (materia == null) return null;

        MateriaDTO dto = MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .prerequisitos(materia.getPrerequisitos() != null ?
                        materia.getPrerequisitos().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                .esPrerequisitoDe(materia.getEsPrerequisitoDe() != null ?
                        materia.getEsPrerequisitoDe().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                .build();

        // Mapear unidades temáticas si existen
        if (materia.getUnidadesTematicas() != null && !materia.getUnidadesTematicas().isEmpty()) {
            dto.setUnidadesTematicas(
                    materia.getUnidadesTematicas().stream()
                            .map(unidad -> {
                                return UnidadTematicaDTO.builder()
                                        .id(unidad.getId())
                                        .titulo(unidad.getTitulo())
                                        .descripcion(unidad.getDescripcion())
                                        .orden(unidad.getOrden())
                                        .materiaId(materia.getId())
                                        .build();
                            })
                            .collect(Collectors.toList())
            );
        }

        return dto;
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
        // Map other fields as necessary
        Materia savedMateria = materiaRepository.save(materia);
        return mapToDTO(savedMateria);
    }

    @Override
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO actualizarMateria(Long id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Materia not found"));
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        // Map other fields as necessary
        Materia updatedMateria = materiaRepository.save(materia);
        return mapToDTO(updatedMateria);
    }

    @Override
    @CacheEvict(value = {"materia", "materias"}, allEntries = true)
    public void eliminarMateria(Long id) {
        materiaRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = {"materias", "unidadesPorMateria"}, allEntries = true)
    public MateriaDTO reemplazarUnidadesTematicas(Long id, List<UnidadTematicaDTO> unidadesDTO) {
        // Obtener la materia por su ID
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Materia not found with id: " + id));

        // Validar que no haya unidades temáticas duplicadas (por título)
        long countDistinct = unidadesDTO.stream()
                .map(UnidadTematicaDTO::getTitulo)
                .distinct()
                .count();

        if (countDistinct < unidadesDTO.size()) {
            throw new IllegalArgumentException("No se permiten unidades temáticas con títulos duplicados");
        }

        // Eliminar todas las unidades temáticas existentes
        // Gracias a orphanRemoval=true, estas unidades serán eliminadas de la base de datos
        materia.getUnidadesTematicas().clear();

        // Agregar las nuevas unidades temáticas
        for (UnidadTematicaDTO unidadDTO : unidadesDTO) {
            UnidadTematica unidad = new UnidadTematica();
            unidad.setTitulo(unidadDTO.getTitulo());
            unidad.setDescripcion(unidadDTO.getDescripcion());
            unidad.setOrden(unidadDTO.getOrden());

            // Establecer la relación bidireccional
            materia.addUnidadTematica(unidad);
        }

        // Guardar la materia actualizada
        // Gracias a cascade=CascadeType.ALL, las nuevas unidades serán guardadas automáticamente
        Materia materiaActualizada = materiaRepository.save(materia);

        return mapToDTO(materiaActualizada);
    }
}
