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
    private IUnidadTematicaService unidadService;

    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<List<UnidadTematicaDTO>> obtenerUnidadesPorMateria(@PathVariable Long materiaId) {
        List<UnidadTematicaDTO> unidades = unidadService.obtenerUnidadesPorMateria(materiaId);
        return ResponseEntity.ok(unidades);
    }

    @PostMapping("/materia/{materiaId}")
    public ResponseEntity<UnidadTematicaDTO> crearUnidad(
            @PathVariable Long materiaId,
            @RequestBody UnidadTematicaDTO unidadDTO) {
        UnidadTematicaDTO nuevaUnidad = unidadService.crearUnidad(unidadDTO, materiaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUnidad);
    }
}