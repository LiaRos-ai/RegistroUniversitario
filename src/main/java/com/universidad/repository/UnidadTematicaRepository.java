package com.universidad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.universidad.model.Docente;
import com.universidad.model.EvaluacionDocente;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;

public interface UnidadTematicaRepository extends JpaRepository<UnidadTematica, Long> {
    
    List<UnidadTematica> findByMateria(Materia materia);
}

