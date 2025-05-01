package com.universidad.repository;

import com.universidad.model.UnidadTematica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadTematicaRepository extends JpaRepository<UnidadTematica, Long> {
}
