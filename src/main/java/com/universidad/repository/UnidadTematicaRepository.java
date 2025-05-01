package com.universidad.repository;

import com.universidad.model.UnidadTematica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UnidadTematicaRepository extends JpaRepository<UnidadTematica, Long> {
    List<UnidadTematica> findByMateriaId(Long materiaId);
}
