package com.universidad.controller;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.service.IUnidadTematicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
public class UnidadTematicaController {

    private final IUnidadTematicaService unidadTematicaService;

    @Autowired
    public UnidadTematicaController(IUnidadTematicaService unidadTematicaService) {
        this.unidadTematicaService = unidadTematicaService;
    }

    // Obtener todas las unidades temáticas
    @GetMapping
    public ResponseEntity<List<UnidadTematicaDTO>> obtenerTodasLasUnidades() {
        List<UnidadTematicaDTO> result = unidadTematicaService.obtenerTodasLasUnidades();
        return ResponseEntity.ok(result);
    }

    // Obtener unidades temáticas por el ID de la materia
    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<List<UnidadTematicaDTO>> obtenerUnidadesPorMateria(@PathVariable Long materiaId) {
        List<UnidadTematicaDTO> unidades = unidadTematicaService.obtenerUnidadesPorMateria(materiaId);
        if (unidades.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(unidades);
    }

    // Crear una nueva unidad temática
    @PostMapping
    public ResponseEntity<UnidadTematicaDTO> crearUnidadTematica(@RequestBody UnidadTematicaDTO unidadTematicaDTO) {
        UnidadTematicaDTO nuevaUnidadTematica = unidadTematicaService.crearUnidadTematica(unidadTematicaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUnidadTematica);
    }

    // Actualizar una unidad temática existente
    @PutMapping("/{id}")
    public ResponseEntity<UnidadTematicaDTO> actualizarUnidadTematica(@PathVariable Long id, @RequestBody UnidadTematicaDTO unidadTematicaDTO) {
        UnidadTematicaDTO unidadActualizada = unidadTematicaService.actualizarUnidadTematica(id, unidadTematicaDTO);
        return ResponseEntity.ok(unidadActualizada);
    }

    // Eliminar una unidad temática por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUnidadTematica(@PathVariable Long id) {
        unidadTematicaService.eliminarUnidadTematica(id);
        return ResponseEntity.noContent().build();
    }
}
