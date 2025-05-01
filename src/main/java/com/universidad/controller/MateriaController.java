package com.universidad.controller;

import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.model.Materia;
import com.universidad.service.IMateriaService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    private final IMateriaService materiaService;
    private static final Logger logger = LoggerFactory.getLogger(MateriaController.class);

    @Autowired
    public MateriaController(IMateriaService materiaService) {
        this.materiaService = materiaService;
    }

    // Obtener todas las materias
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> obtenerTodasLasMaterias() {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerTodasLasMaterias: {}", inicio);
        List<MateriaDTO> result = materiaService.obtenerTodasLasMaterias();
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerTodasLasMaterias: {} (Duracion: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(result);
    }

    // Obtener una materia por su ID
    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerMateriaPorId: {}", inicio);
        MateriaDTO materia = materiaService.obtenerMateriaPorId(id);
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerMateriaPorId: {} (Duracion: {} ms)", fin, (fin - inicio));
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    // Obtener una materia por su código único
    @GetMapping("/codigo/{codigoUnico}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorCodigoUnico(@PathVariable String codigoUnico) {
        MateriaDTO materia = materiaService.obtenerMateriaPorCodigoUnico(codigoUnico);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    // Crear una nueva materia
    @PostMapping
    public ResponseEntity<MateriaDTO> crearMateria(@RequestBody MateriaDTO materiaDTO) {
        MateriaDTO nuevaMateria = materiaService.crearMateria(materiaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMateria);
    }

    // Actualizar una materia existente
    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> actualizarMateria(@PathVariable Long id, @RequestBody MateriaDTO materiaDTO) {
        MateriaDTO materiaActualizada = materiaService.actualizarMateria(id, materiaDTO);
        return ResponseEntity.ok(materiaActualizada);
    }

    // Eliminar una materia por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

    // Verificar si agregar el prerequisito formaría un ciclo
    @GetMapping("/formaria-circulo/{materiaId}/{prerequisitoId}")
    @Transactional
    public ResponseEntity<Boolean> formariaCirculo(@PathVariable Long materiaId, @PathVariable Long prerequisitoId) {
        MateriaDTO materiaDTO = materiaService.obtenerMateriaPorId(materiaId);
        if (materiaDTO == null) {
            return ResponseEntity.notFound().build();
        }
        Materia materia = new Materia(materiaDTO.getId(), materiaDTO.getNombreMateria(), materiaDTO.getCodigoUnico());
        boolean circulo = materia.formariaCirculo(prerequisitoId);
        if (circulo) {
            return ResponseEntity.badRequest().body(circulo);
        }
        return ResponseEntity.ok(circulo);
    }

    @GetMapping("/{id}/unidades")
    public ResponseEntity<List<UnidadTematicaDTO>> obtenerUnidadesPorMateria(@PathVariable Long id) {
        List<UnidadTematicaDTO> unidades = materiaService.obtenerUnidadesPorMateria(id);
        if (unidades.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(unidades);
    }
    // Reemplazar las unidades temáticas de una materia
    @PutMapping("/{id}/unidades")
    public ResponseEntity<MateriaDTO> actualizarUnidadesPorMateria(@PathVariable Long id, @RequestBody List<UnidadTematicaDTO> nuevasUnidades) {
        try {
            // Llamar al servicio para actualizar las unidades temáticas de la materia
            MateriaDTO materiaDTO = materiaService.actualizarUnidadesPorMateria(id, nuevasUnidades);
            return ResponseEntity.ok(materiaDTO);
        } catch (Exception e) {
            logger.error("[MATERIA] Error al actualizar las unidades temáticas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
