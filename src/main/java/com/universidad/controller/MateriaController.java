package com.universidad.controller;

import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.service.IMateriaService;
import com.universidad.service.IUnidadTematicaService;

import org.springframework.transaction.annotation.Transactional;

import com.universidad.dto.MateriaDTO;
import com.universidad.dto.UnidadTematicaDTO;
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
    private final IUnidadTematicaService unidadTematicaService;
    private static final Logger logger = LoggerFactory.getLogger(MateriaController.class);

    @Autowired
    public MateriaController(IMateriaService materiaService, IUnidadTematicaService unidadTematicaService) {
        this.materiaService = materiaService;
        this.unidadTematicaService = unidadTematicaService;
    }

    @GetMapping
    public ResponseEntity<List<MateriaDTO>> obtenerTodasLasMaterias() {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerTodasLasMaterias: {}", inicio);
        List<MateriaDTO> result = materiaService.obtenerTodasLasMaterias();
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerTodasLasMaterias: {} (Duracion: {} ms)", fin, (fin-inicio));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerMateriaPorId: {}", inicio);
        MateriaDTO materia = materiaService.obtenerMateriaPorId(id);
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerMateriaPorId: {} (Duracion: {} ms)", fin, (fin-inicio));
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @GetMapping("/codigo/{codigoUnico}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorCodigoUnico(@PathVariable String codigoUnico) {
        MateriaDTO materia = materiaService.obtenerMateriaPorCodigoUnico(codigoUnico);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @PostMapping
    public ResponseEntity<MateriaDTO> crearMateria(@RequestBody MateriaDTO materia) {
        //MateriaDTO materiaDTO = new MateriaDTO(materia.getId(), materia.getNombre(), materia.getCodigoUnico());
        MateriaDTO nueva = materiaService.crearMateria(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> actualizarMateria(@PathVariable Long id, @RequestBody MateriaDTO materia) {
        //MateriaDTO materiaDTO = new MateriaDTO(materia.getId(), materia.getNombreMateria(), materia.getCodigoUnico());
        MateriaDTO actualizadaDTO = materiaService.actualizarMateria(id, materia);
        //Materia actualizada = new Materia(actualizadaDTO.getId(), actualizadaDTO.getNombre(), actualizadaDTO.getCodigoUnico());
        return ResponseEntity.ok(actualizadaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene todas las unidades temáticas de una materia.
     * @param id ID de la materia
     * @return Lista de unidades temáticas
     */
    @GetMapping("/{id}/unidades")
    @Transactional(readOnly = true) // Agregamos transacción de solo lectura
    public ResponseEntity<List<UnidadTematicaDTO>> obtenerUnidadesPorMateriaId(@PathVariable Long id) {
        MateriaDTO materia = materiaService.obtenerMateriaPorId(id);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        List<UnidadTematicaDTO> unidades = unidadTematicaService.obtenerUnidadesPorMateriaId(id);
        return ResponseEntity.ok(unidades);
    }

    /**
     * Obtiene todas las materias con sus unidades temáticas incluidas.
     * @return Lista de materias con unidades temáticas
     */
    @GetMapping("/con-unidades")
    public ResponseEntity<List<MateriaDTO>> obtenerTodasLasMateriasConUnidades() {
        List<MateriaDTO> materias = materiaService.obtenerTodasLasMaterias();
        return ResponseEntity.ok(materias);
    }

    /**
     * Reemplaza todas las unidades temáticas de una materia.
     * @param id ID de la materia
     * @param unidades Lista de nuevas unidades temáticas
     * @return La materia actualizada con sus nuevas unidades temáticas
     */
    @PutMapping("/{id}/unidades")
    public ResponseEntity<?> reemplazarUnidadesTematicas(
            @PathVariable Long id,
            @RequestBody List<UnidadTematicaDTO> unidades) {
        try {
            MateriaDTO materiaActualizada = materiaService.reemplazarUnidadesTematicas(id, unidades);
            return ResponseEntity.ok(materiaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/formaria-circulo/{materiaId}/{prerequisitoId}") // Endpoint para verificar si una materia formaría un círculo con un prerequisito
    @Transactional// Anotación que indica que este método debe ejecutarse dentro de una transacción de solo lectura
    public ResponseEntity<Boolean> formariaCirculo(@PathVariable Long materiaId, @PathVariable Long prerequisitoId) {
        MateriaDTO materiaDTO = materiaService.obtenerMateriaPorId(materiaId); // Obtiene la materia por su ID
        if (materiaDTO == null) { // Verifica si la materia existe
            return ResponseEntity.notFound().build();
        }
        Materia materia = new Materia(materiaDTO.getId(), materiaDTO.getNombreMateria(), materiaDTO.getCodigoUnico());
        // Crea una nueva instancia de Materia con los datos obtenidos
        // Verifica si agregar el prerequisito formaría un círculo
        boolean circulo = materia.formariaCirculo(prerequisitoId); // Llama al método formariaCirculo de la clase Materia
        if (circulo) { // Si formaría un círculo, retorna un error 400 Bad Request
            return ResponseEntity.badRequest().body(circulo);
        }
        return ResponseEntity.ok(circulo);
    }
}
