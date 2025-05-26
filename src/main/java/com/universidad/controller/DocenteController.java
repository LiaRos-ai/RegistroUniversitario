package com.universidad.controller;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;
import com.universidad.service.IDocenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@Tag(name = "Docentes", description = "Operaciones CRUD y de asignación de materias a docentes")
@Validated
public class DocenteController {
    @Autowired
    private IDocenteService docenteService;

    @Operation(summary = "Asignar materia a docente", description = "Asigna una materia a un docente por sus IDs.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Materia asignada al docente"),
        @ApiResponse(responseCode = "400", description = "Error de validación o negocio")
    })
    @PostMapping("/{docenteId}/materias/{materiaId}")
    public ResponseEntity<?> asignarMateriaADocente(
            @Parameter(description = "ID del docente", example = "1") @PathVariable Long docenteId,
            @Parameter(description = "ID de la materia", example = "2") @PathVariable Long materiaId) {
        docenteService.asignarMateria(docenteId, materiaId);
        return ResponseEntity.ok().body("Materia asignada al docente");
    }

    @Operation(summary = "Desasignar materia de docente", description = "Desasigna una materia de un docente por sus IDs.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Materia desasignada del docente"),
        @ApiResponse(responseCode = "400", description = "Error de validación o negocio")
    })
    @DeleteMapping("/{docenteId}/materias/{materiaId}")
    public ResponseEntity<?> desasignarMateriaADocente(
            @Parameter(description = "ID del docente", example = "1") @PathVariable Long docenteId,
            @Parameter(description = "ID de la materia", example = "2") @PathVariable Long materiaId) {
        docenteService.desasignarMateria(docenteId, materiaId);
        return ResponseEntity.ok().body("Materia desasignada del docente");
    }

    @Operation(summary = "Crear docente", description = "Crea un nuevo docente.")
    @ApiResponse(responseCode = "201", description = "Docente creado")
    @PostMapping
    public ResponseEntity<DocenteDTO> crearDocente(@Valid @RequestBody DocenteDTO docente) {
        DocenteDTO nuevo = docenteService.guardarDocente(docente);
        return ResponseEntity.status(201).body(nuevo);
    }

    @Operation(summary = "Actualizar docente", description = "Actualiza los datos de un docente existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente actualizado"),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    @PutMapping("/{docenteId}")
    public ResponseEntity<DocenteDTO> actualizarDocente(
            @Parameter(description = "ID del docente", example = "1") @PathVariable Long docenteId,
            @Valid @RequestBody DocenteDTO docente) {
        DocenteDTO actualizado = docenteService.actualizarDocente(docenteId, docente);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Obtener docente", description = "Obtiene un docente por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente encontrado"),
        @ApiResponse(responseCode = "400", description = "Error de validación o negocio")
    })
    @GetMapping("/{docenteId}")
    public ResponseEntity<DocenteDTO> obtenerDocente(
            @Parameter(description = "ID del docente", example = "1") @PathVariable Long docenteId) {
        DocenteDTO docente = docenteService.obtenerDocentePorId(docenteId);
        return ResponseEntity.ok(docente);
    }

    @Operation(summary = "Listar docentes", description = "Obtiene la lista de todos los docentes.")
    @ApiResponse(responseCode = "200", description = "Lista de docentes")
    @GetMapping
    public ResponseEntity<List<DocenteDTO>> listarDocentes() {
        return ResponseEntity.ok(docenteService.listarDocentes());
    }
}
