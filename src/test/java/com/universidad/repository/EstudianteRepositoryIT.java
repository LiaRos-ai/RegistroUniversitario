package com.universidad.repository;

import com.universidad.model.Estudiante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EstudianteRepositoryIT {
    @Autowired
    private EstudianteRepository repo;

    @Test
    void guardarYBuscarEstudiante() {
        Estudiante estudiante = Estudiante.builder()
            .nombre("Ana")
            .apellido("López")
            .email("ana@uni.edu")
            .fechaNacimiento(LocalDate.of(1999, 5, 10))
            .numeroInscripcion("A123")
            .fechaAlta(LocalDate.now()) 
            .build();
        repo.save(estudiante);
        assertThat(repo.findByNumeroInscripcion("A123")).isNotNull();
    }
}
