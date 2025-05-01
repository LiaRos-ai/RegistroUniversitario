package com.universidad.repository;

import com.universidad.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
     @Query("SELECT m FROM Materia m LEFT JOIN FETCH m.unidadesTematicas WHERE m.id = :id")
    Optional<Materia> findByIdWithUnidades(@Param("id") Long id);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Materia> findById(Long id);
    
    Materia findByCodigoUnico(String codigoUnico);
}
