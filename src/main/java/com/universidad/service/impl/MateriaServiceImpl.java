package com.universidad.service.impl;

import com.universidad.model.Materia;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.UnidadTematica;
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

   // TAREA GRUPO
   @Override
   @Transactional
   public List<MateriaDTO> listarMateriasConUnidades() {
       return materiaRepository.findAll().stream().map(materia -> {
           MateriaDTO dto = MateriaDTO.builder()
                   .id(materia.getId())
                   .nombreMateria(materia.getNombreMateria())
                   .codigoUnico(materia.getCodigoUnico())
                   .creditos(materia.getCreditos())
                   .prerequisitos(materia.getPrerequisitos() != null ?
                           materia.getPrerequisitos().stream().map(Materia::getId).collect(java.util.stream.Collectors.toList()) : null)
                   .esPrerequisitoDe(materia.getEsPrerequisitoDe() != null ?
                           materia.getEsPrerequisitoDe().stream().map(Materia::getId).collect(java.util.stream.Collectors.toList()) : null)
                   .unidadesTematicas(materia.getUnidades() != null ?
                           materia.getUnidades().stream().map(unidad ->
                                   com.universidad.dto.UnidadTematicaDTO.builder()
                                           .id(unidad.getId())
                                           .titulo(unidad.getTitulo())
                                           .descripcion(unidad.getDescripcion())
                                           .build()
                           ).collect(java.util.stream.Collectors.toList()) : null)
                   .build();
           return dto;
       }).collect(java.util.stream.Collectors.toList());
   }

    //PARTE 2 , anadimos la funcion  reemplazarUnidadesTematicas
    @Override
    @Transactional
    public MateriaDTO reemplazarUnidadesTematicas(Long id, List<UnidadTematicaDTO> nuevasUnidades) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        // Elimina las unidades temáticas actuales
        materia.getUnidades().clear();
        // Anade nuevas unidades evitando duplicados
        for (UnidadTematicaDTO dto : nuevasUnidades.stream().distinct().toList()) {
            UnidadTematica unidad = new UnidadTematica();
            unidad.setTitulo(dto.getTitulo());
            unidad.setDescripcion(dto.getDescripcion());
            unidad.setMateria(materia); // Establece la relación inversa
            materia.getUnidades().add(unidad);
        }
        materia = materiaRepository.save(materia);
        // Convertir la entidad actualizada a DTO
        List<UnidadTematicaDTO> unidadesDTO = materia.getUnidades().stream()
                .map(u -> UnidadTematicaDTO.builder()
                        .id(u.getId())
                        .titulo(u.getTitulo())
                        .descripcion(u.getDescripcion())
                        .build())
                .collect(Collectors.toList());
        return MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .unidadesTematicas(unidadesDTO)
                .build();
    }




}
