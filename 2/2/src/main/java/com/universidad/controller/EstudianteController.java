package com.universidad.controller;

import com.universidad.dto.EstudianteDTO;
import com.universidad.model.Materia;
import com.universidad.service.IEstudianteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final IEstudianteService estudianteService;

    @Autowired
    public EstudianteController(IEstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> obtenerTodosLosEstudiantes() {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodosLosEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/inscripcion/{numeroInscripcion}")
    public ResponseEntity<?> obtenerEstudiantePorNumeroInscripcion(@PathVariable String numeroInscripcion) {
        try {
            EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorNumeroInscripcion(numeroInscripcion);
            return ResponseEntity.ok(estudiante);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estudiante con número de inscripción " + numeroInscripcion + " no encontrado.");
        }
    }

    @GetMapping("/{id}/materias")
    public ResponseEntity<?> obtenerMateriasDeEstudiante(@PathVariable("id") Long estudianteId) {
        Optional<List<Materia>> materiasOpt = estudianteService.obtenerMateriasDeEstudiante(estudianteId);

        return materiasOpt
            .map(materias -> {
                if (materias.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                } else {
                    return ResponseEntity.ok().body(materias);
                }
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                               .body("No se encontró el estudiante con ID: " + estudianteId));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EstudianteDTO> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO nuevoEstudiante = estudianteService.crearEstudiante(estudianteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEstudiante);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        try {
            EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, estudianteDTO);
            return ResponseEntity.ok(estudianteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante con ID " + id + " no encontrado.");
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EstudianteDTO>> obtenerEstudianteActivo() {
        List<EstudianteDTO> estudiantesActivos = estudianteService.obtenerEstudianteActivo();
        return ResponseEntity.ok(estudiantesActivos);
    }
}


