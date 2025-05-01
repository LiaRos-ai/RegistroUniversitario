package com.universidad.repository;

import com.universidad.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente,Long> {
    List<Docente> findByDepartamento(String departamento);
=======
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    // Puedes agregar métodos personalizados si lo necesitas
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
}
