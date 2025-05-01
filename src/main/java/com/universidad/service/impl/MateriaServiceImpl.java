package com.universidad.service.impl;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.dto.MateriaDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    // Método utilitario para mapear Materia a MateriaDTO
    private MateriaDTO mapToDTO(Materia materia) {
        if (materia == null) return null;
        return MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .prerequisitos(materia.getPrerequisitos() != null ?
                    materia.getPrerequisitos().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                .esPrerequisitoDe(materia.getEsPrerequisitoDe() != null ?
                    materia.getEsPrerequisitoDe().stream().map(Materia::getId).collect(Collectors.toList()) : null)
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
    public void reemplazarUnidades(Long idMateria, List<UnidadTematicaDTO> nuevasUnidades) {
        Materia materia = materiaRepository.findById(idMateria)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        // Validar nombres duplicados
        Set<String> nombres = new HashSet<>();
        for (UnidadTematicaDTO dto : nuevasUnidades) {
            if (!nombres.add(dto.getNombre_unidad().toLowerCase())) {
                throw new RuntimeException("No se permiten unidades temáticas duplicadas por nombre");
            }
        }

        // Eliminar todas las unidades anteriores
        materia.getUnidades().clear();

        // Crear nuevas unidades
        List<UnidadTematica> nuevas = nuevasUnidades.stream()
                .map(dto -> UnidadTematica.builder()
                        .nombre_unidad(dto.getNombre_unidad())
                        .descripcion(dto.getDescripcion())
                        .contenido(dto.getContenido())
                        .materia(materia)
                        .build())
                .collect(Collectors.toList());

        materia.getUnidades().addAll(nuevas);
        materiaRepository.save(materia); // Persistencia en cascada
    }
    @Override
    public List<Materia> obtenerMateriasConUnidades() {
        return materiaRepository.findAll();
    }

}
