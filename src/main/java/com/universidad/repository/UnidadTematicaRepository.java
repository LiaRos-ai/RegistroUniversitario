package com.universidad.repository;

import com.universidad.model.UnidadTematica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadTematicaRepository extends JpaRepository<UnidadTematica, Long> {
    List<UnidadTematica> findByMateriaId(Long materiaId);
    void deleteByMateriaId(Long materiaId);
}
