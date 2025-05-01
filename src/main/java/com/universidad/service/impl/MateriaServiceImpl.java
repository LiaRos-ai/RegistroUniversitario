package com.universidad.service.impl;

import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadTematicaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    // Convertir UnidadTematica a UnidadTematicaDTO
    private UnidadTematicaDTO convertirUnidadTematicaADTO(UnidadTematica unidad) {
        return UnidadTematicaDTO.builder()
                .id(unidad.getId())
                .tema(unidad.getTema())
                .descripcion(unidad.getDescripcion())
                .materiaId(unidad.getMateria().getId())  // Relacionando la unidad con la materia
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

    /**
     * Actualiza las unidades temáticas asociadas a una materia.
     * Este método reemplaza todas las unidades temáticas existentes por las nuevas enviadas en la solicitud.
     * Se evita duplicar unidades temáticas con el mismo tema.
     */
    @Override
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO actualizarUnidadesPorMateria(Long id, List<UnidadTematicaDTO> nuevasUnidades) {
        // Obtener la materia por su ID
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));

        // Limpiar las unidades temáticas actuales (esto se maneja con orphanRemoval = true)
        materia.getUnidadesTematicas().clear();

        // Agregar las nuevas unidades temáticas solo si no existen duplicados
        for (UnidadTematicaDTO unidadDTO : nuevasUnidades) {
            // Verificar si ya existe una unidad con el mismo tema
            boolean existeUnidad = materia.getUnidadesTematicas().stream()
                    .anyMatch(u -> u.getTema().equals(unidadDTO.getTema()));
            if (!existeUnidad) {
                UnidadTematica nuevaUnidad = new UnidadTematica();
                nuevaUnidad.setTema(unidadDTO.getTema());
                nuevaUnidad.setDescripcion(unidadDTO.getDescripcion());
                nuevaUnidad.setMateria(materia);  // Relacionamos la nueva unidad con la materia
                materia.addUnidadTematica(nuevaUnidad);  // Usamos el método helper para agregar la unidad
            }
        }

        // Guardamos la materia con las nuevas unidades
        materiaRepository.save(materia);

        // Devolvemos el DTO actualizado de la materia
        return mapToDTO(materia);
    }

    /**
     * Obtiene todas las unidades temáticas asociadas a una materia.
     * 
     * @param materiaId el ID de la materia
     * @return Lista de unidades temáticas asociadas a la materia
     */
    @Override
    public List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId) {
        // Obtener la materia por su ID
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada con ID: " + materiaId));

        // Obtener las unidades temáticas de la materia y convertirlas a DTO
        return materia.getUnidadesTematicas().stream()
                .map(this::convertirUnidadTematicaADTO)
                .collect(Collectors.toList());
    }
}
