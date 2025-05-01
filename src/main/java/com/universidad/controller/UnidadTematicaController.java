package com.universidad.controller;
import com.universidad.model.Docente;
import com.universidad.model.EvaluacionDocente;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.service.IUnidadTematicaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/unidad-tematica")
public class UnidadTematicaController {
    @Autowired
    private IUnidadTematicaService unidadTematicaService;

    @PostMapping
    public ResponseEntity<UnidadTematica> crearEvaluacion(@RequestBody UnidadTematica evaluacion) {
        UnidadTematica nueva = unidadTematicaService.crearUnidadTematica(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
    @GetMapping("/materia/{id}")
    public ResponseEntity<List<UnidadTematica>> obtenerEvaluacionesPorDocente(@PathVariable Long id) {
        List<UnidadTematica> evaluaciones = unidadTematicaService.obtenerUnidadTematicaPorMateria(id);
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping
    public ResponseEntity<List<Materia>> obtenerDocentesConEvaluaciones() {
        List<Materia> docentes = unidadTematicaService.obtenerTodaUnidadTematica();
        return ResponseEntity.ok(docentes);
    }

    @PutMapping("/materias/{id}/unidades")
    public ResponseEntity<UnidadTematica> actualizarMateria(@PathVariable Long id, @RequestBody UnidadTematica unidadTematica) {
        //MateriaDTO materiaDTO = new MateriaDTO(materia.getId(), materia.getNombreMateria(), materia.getCodigoUnico());
        UnidadTematica actualizadaDTO = unidadTematicaService.actualizarUnidadTematica(id, unidadTematica);
        //Materia actualizada = new Materia(actualizadaDTO.getId(), actualizadaDTO.getNombre(), actualizadaDTO.getCodigoUnico());
        return ResponseEntity.ok(actualizadaDTO);
    }
}
