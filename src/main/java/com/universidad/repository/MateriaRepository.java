package com.universidad.repository;

import com.universidad.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MateriaRepository extends JpaRepository<Materia,Long> {

}
