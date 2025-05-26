package com.universidad.service;

import com.universidad.dto.InscripcionDTO;
import java.util.List;

public interface IInscripcionService {
    InscripcionDTO crearInscripcion(Long estudianteId, Long materiaId);
    InscripcionDTO actualizarInscripcion(Long inscripcionId, String nuevoEstado);
    void eliminarInscripcion(Long inscripcionId);
    List<InscripcionDTO> listarInscripcionesPorEstudiante(Long estudianteId);
    List<InscripcionDTO> listarInscripcionesPorMateria(Long materiaId);
    InscripcionDTO obtenerInscripcion(Long inscripcionId);
}
