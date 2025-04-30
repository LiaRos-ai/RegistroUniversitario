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

    @Autowired
    private IUnidadTematicaService unidadTematicaService;

    @GetMapping
    public ResponseEntity<List<UnidadTematicaDTO>> obtenerTodasLasUnidades() {
        List<UnidadTematicaDTO> unidades = unidadTematicaService.obtenerTodasLasUnidades();
        return ResponseEntity.ok(unidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadTematicaDTO> obtenerUnidadPorId(@PathVariable Long id) {
        UnidadTematicaDTO unidad = unidadTematicaService.obtenerUnidadPorId(id);
        if (unidad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(unidad);
    }

    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<List<UnidadTematicaDTO>> obtenerUnidadesPorMateriaId(@PathVariable Long materiaId) {
        List<UnidadTematicaDTO> unidades = unidadTematicaService.obtenerUnidadesPorMateriaId(materiaId);
        return ResponseEntity.ok(unidades);
    }

    @PostMapping
    public ResponseEntity<UnidadTematicaDTO> crearUnidad(@RequestBody UnidadTematicaDTO unidadDTO) {
        UnidadTematicaDTO nuevaUnidad = unidadTematicaService.crearUnidad(unidadDTO);
        return new ResponseEntity<>(nuevaUnidad, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadTematicaDTO> actualizarUnidad(@PathVariable Long id, @RequestBody UnidadTematicaDTO unidadDTO) {
        UnidadTematicaDTO unidadActualizada = unidadTematicaService.actualizarUnidad(id, unidadDTO);
        return ResponseEntity.ok(unidadActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUnidad(@PathVariable Long id) {
        unidadTematicaService.eliminarUnidad(id);
        return ResponseEntity.noContent().build();
    }
}
