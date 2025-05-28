package com.universidad.service;

import com.universidad.dto.EstudianteDTO;
import com.universidad.model.Estudiante;
import com.universidad.repository.EstudianteRepository;
import com.universidad.service.impl.EstudianteServiceImpl;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EstudianteServiceTest {
    @Test
    void obtenerEstudiantePorId_devuelveDTO() {
        EstudianteRepository repo = mock(EstudianteRepository.class);
        EstudianteServiceImpl service = new EstudianteServiceImpl(repo);
        Estudiante estudiante = Estudiante.builder().id(1L).nombre("Juan").build();
        when(repo.findById(1L)).thenReturn(Optional.of(estudiante));
        EstudianteDTO dto = service.obtenerEstudiantePorId(1L);
        assertThat(dto.getNombre()).isEqualTo("Juan");
    }
}
