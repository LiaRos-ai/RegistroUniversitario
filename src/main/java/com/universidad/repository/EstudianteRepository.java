package com.universidad.repository; // Define el paquete al que pertenece esta clase

import com.universidad.model.Estudiante; // Importa la clase Estudiante del paquete model
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; // Importa la anotación Repository de Spring

import java.util.Optional;

@Repository // Anotación que indica que esta clase es un repositorio de Spring
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    // No es necesario implementar métodos básicos como findAll, ya que JpaRepository los proporciona automáticamente.

    // Método para encontrar un estudiante por su número de inscripción
    Estudiante findByNumeroInscripcion(String numeroInscripcion); 

    // Método para encontrar un estudiante por su estado
    Estudiante findByEstado(String estado); // Método para encontrar un estudiante por su estado
    // Nuevo método para encontrar estudiante con sus materias
    Optional<Estudiante> findById(Long id);



}