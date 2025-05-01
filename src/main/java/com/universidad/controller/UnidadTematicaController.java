package com.universidad.controller;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.service.UnidadTematicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@RequiredArgsConstructor
public class UnidadTematicaController {

    private final UnidadTematicaService unidadService;

    @PostMapping
    public ResponseEntity<UnidadTematicaDTO> crearUnidad(@RequestBody UnidadTematicaDTO unidadDTO) {
        return ResponseEntity.ok(unidadService.crearUnidad(unidadDTO));
    }

    @GetMapping("/materia/{idMateria}")
    public ResponseEntity<List<UnidadTematicaDTO>> listarPorMateria(@PathVariable Long idMateria) {
        return ResponseEntity.ok(unidadService.listarPorMateria(idMateria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUnidad(@PathVariable Long id) {
        unidadService.eliminarUnidad(id);
        return ResponseEntity.noContent().build();
    }
}
