package com.universidad.service.impl;

import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.dto.MateriaDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private UnidadTematicaRepository unidadTematicaRepository;


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
    public List<UnidadTematica> obtenerUnidadesPorMateria(Long id) {
        List<UnidadTematica> unidades = unidadTematicaRepository.findByMateriaId(id);
        if (unidades.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron unidades temáticas para la materia con ID: " + id);
        }
        return unidades;
    }


    @Override
    public List<Materia> obtenerMateriasConUnidades(){
        return materiaRepository.findAllWithUnidades();
    }


    @Override
    @Transactional
    public void reemplazarUnidades(Long materiaId, List<UnidadTematica> nuevasUnidades) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        //hasta aqui sirve lo demas que chatgpt quiera o corrija
        Set<String> nombresUnicos = new HashSet<>();
        for (UnidadTematica unidad : nuevasUnidades) {
            String nombre = unidad.getTitulo().trim().toLowerCase();
            if (!nombresUnicos.add(nombre)) {
                throw new RuntimeException("Unidad duplicada: " + unidad.getTitulo());
            }
            unidad.setMateria(materia);
        }


        materia.getUnidades().clear();
        materia.getUnidades().addAll(nuevasUnidades);

        materiaRepository.save(materia);
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
}
