package com.universidad.repository;

import com.universidad.model.Docente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
    // Puedes agregar métodos personalizados si lo necesitas
    @Query("SELECT d FROM Docente d LEFT JOIN FETCH d.evaluaciones")
    List<Docente> findAllWithEvaluaciones();
}
