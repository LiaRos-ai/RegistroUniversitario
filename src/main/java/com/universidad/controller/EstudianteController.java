package com.universidad.controller;

import com.universidad.dto.EstudianteDTO;
import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import com.universidad.service.IEstudianteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorNumeroInscripcion(@PathVariable String numeroInscripcion) {
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorNumeroInscripcion(numeroInscripcion);
        return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/{id}/materias")
    public ResponseEntity<List<Materia>> obtenerMateriasDeEstudiante(@PathVariable("id") Long estudianteId) {
        List<Materia> materias = estudianteService.obtenerMateriasDeEstudiante(estudianteId);
        return ResponseEntity.ok(materias);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EstudianteDTO> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO nuevoEstudiante = estudianteService.crearEstudiante(estudianteDTO);
        return ResponseEntity.status(201).body(nuevoEstudiante);
    }

    @PutMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, estudianteDTO);
        return ResponseEntity.ok(estudianteActualizado);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EstudianteDTO>> obtenerEstudianteActivo() {
        List<EstudianteDTO> estudiantesActivos = estudianteService.obtenerEstudianteActivo();
        return ResponseEntity.ok(estudiantesActivos);
    }

    @PutMapping("/eliminar/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EstudianteDTO> eliminarId(@PathVariable Long id) {
        EstudianteDTO estudianteActualizado = estudianteService.eliminarId(id);
        return ResponseEntity.ok(estudianteActualizado);
    }


    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<EstudianteDTO> buscarPorNombre(@PathVariable String nombre){
        System.out.println("Entro a buscar");
        EstudianteDTO estudianteDTO = estudianteService.buscarPorNombre(nombre);
        if(estudianteDTO == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(estudianteDTO);
        }
    }
    @GetMapping("/ordenar")
    public ResponseEntity<List<EstudianteDTO>> mostrarOrdenado() {
        List<EstudianteDTO> estudiantes = estudianteService.ordenarPorApellido();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/materias/{id}")
    public ResponseEntity<List<MateriaDTO>> mostrarEstudianteConInscripciones(@PathVariable Long id){
        List<MateriaDTO> materiaDTOS = estudianteService.materiasInscritasSegunId(id);
        return ResponseEntity.ok(materiaDTOS);
    }
}

