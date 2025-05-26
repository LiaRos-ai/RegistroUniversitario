package com.universidad.repository;

import com.universidad.model.Inscripcion;
import com.universidad.model.Estudiante;
import com.universidad.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByEstudianteId(Long estudianteId);
    List<Inscripcion> findByMateriaId(Long materiaId);
    Optional<Inscripcion> findByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId);
}
