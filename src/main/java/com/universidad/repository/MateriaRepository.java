package com.universidad.repository;

import com.universidad.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    // Buscar materia por código único
    Materia findByCodigoUnico(String codigoUnico);

    // Buscar materia por ID (utilizado con bloqueo pesimista si es necesario)
    Optional<Materia> findById(Long id);
}
