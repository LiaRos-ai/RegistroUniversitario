package com.universidad.repository;

import com.universidad.model.UnidadTematica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadTematicaRepository extends JpaRepository<UnidadTematica, Long> {

    // Buscar unidades temáticas por el ID de la materia
    List<UnidadTematica> findByMateriaId(Long materiaId);

    // Buscar unidad temática por ID
    Optional<UnidadTematica> findById(Long id);
}