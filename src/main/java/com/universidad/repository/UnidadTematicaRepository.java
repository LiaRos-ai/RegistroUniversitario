package com.universidad.repository;

import com.universidad.model.UnidadTematica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadTematicaRepository extends JpaRepository<UnidadTematica, Long> {

    /**
     * Encuentra todas las unidades temáticas asociadas a una materia.
     * @param materiaId ID de la materia
     * @return Lista de unidades temáticas
     */
    List<UnidadTematica> findByMateriaId(Long materiaId);

    /**
     * Encuentra todas las unidades temáticas ordenadas por el campo orden.
     * @param materiaId ID de la materia
     * @return Lista de unidades temáticas ordenadas
     */
    List<UnidadTematica> findByMateriaIdOrderByOrdenAsc(Long materiaId);
}
