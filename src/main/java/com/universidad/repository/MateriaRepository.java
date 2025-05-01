package com.universidad.repository;

import com.universidad.model.Docente;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    @Query("SELECT m FROM Materia m LEFT JOIN FETCH m.unidadTematica")
    List<Materia> findAllWithUnidadTematica();

    Materia findByCodigoUnico(String codigoUnico);
}
