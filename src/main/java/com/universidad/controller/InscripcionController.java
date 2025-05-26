package com.universidad.controller;

import com.universidad.dto.InscripcionDTO;
import com.universidad.model.Inscripcion;
import com.universidad.service.IInscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@Tag(name = "Inscripciones", description = "CRUD de inscripciones de estudiantes en materias")
public class InscripcionController {
    @Autowired
    private IInscripcionService inscripcionService;

    @Operation(summary = "Crear inscripción", description = "Registra una nueva inscripción de un estudiante en una materia.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripción creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación o negocio")
    })
    @PostMapping
    public ResponseEntity<InscripcionDTO> crearInscripcion(
            @Parameter(description = "ID del estudiante", example = "10") @RequestParam Long estudianteId,
            @Parameter(description = "ID de la materia", example = "5") @RequestParam Long materiaId) {
        InscripcionDTO inscripcion = inscripcionService.crearInscripcion(estudianteId, materiaId);
        return ResponseEntity.ok(inscripcion);
    }

    @Operation(summary = "Actualizar inscripción", description = "Actualiza el estado de una inscripción.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripción actualizada"),
        @ApiResponse(responseCode = "400", description = "Error de validación o negocio")
    })
    @PutMapping("/{id}")
    public ResponseEntity<InscripcionDTO> actualizarInscripcion(
            @Parameter(description = "ID de la inscripción", example = "1") @PathVariable Long id,
            @Parameter(description = "Nuevo estado", example = "ANULADA") @RequestParam String estado) {
        InscripcionDTO inscripcion = inscripcionService.actualizarInscripcion(id, estado);
        return ResponseEntity.ok(inscripcion);
    }

    @Operation(summary = "Eliminar inscripción", description = "Elimina una inscripción por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripción eliminada"),
        @ApiResponse(responseCode = "400", description = "Error de validación o negocio")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInscripcion(
            @Parameter(description = "ID de la inscripción", example = "1") @PathVariable Long id) {
        inscripcionService.eliminarInscripcion(id);
        return ResponseEntity.ok("Inscripción eliminada");
    }

    @Operation(summary = "Listar inscripciones por estudiante", description = "Obtiene todas las inscripciones de un estudiante.")
    @ApiResponse(responseCode = "200", description = "Lista de inscripciones")
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<InscripcionDTO>> listarPorEstudiante(
            @Parameter(description = "ID del estudiante", example = "10") @PathVariable Long estudianteId) {
        return ResponseEntity.ok(inscripcionService.listarInscripcionesPorEstudiante(estudianteId));
    }

    @Operation(summary = "Listar inscripciones por materia", description = "Obtiene todas las inscripciones de una materia.")
    @ApiResponse(responseCode = "200", description = "Lista de inscripciones")
    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<List<InscripcionDTO>> listarPorMateria(
            @Parameter(description = "ID de la materia", example = "5") @PathVariable Long materiaId) {
        return ResponseEntity.ok(inscripcionService.listarInscripcionesPorMateria(materiaId));
    }

    @Operation(summary = "Obtener inscripción", description = "Obtiene una inscripción por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripción encontrada"),
        @ApiResponse(responseCode = "400", description = "Error de validación o negocio")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> obtenerInscripcion(
            @Parameter(description = "ID de la inscripción", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.obtenerInscripcion(id));
    }
}
