package com.universidad.controller;

import com.universidad.model.Materia;
import com.universidad.service.IMateriaService;

import jakarta.transaction.Transactional;

import com.universidad.dto.MateriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
@Tag(name = "Materias", description = "Operaciones CRUD y consulta de materias")
public class MateriaController {

    private final IMateriaService materiaService;
    private static final Logger logger = LoggerFactory.getLogger(MateriaController.class);

    @Autowired
    public MateriaController(IMateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @Operation(summary = "Obtener todas las materias", description = "Devuelve la lista de todas las materias registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de materias")
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> obtenerTodasLasMaterias() {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerTodasLasMaterias: {}", inicio);
        List<MateriaDTO> result = materiaService.obtenerTodasLasMaterias();
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerTodasLasMaterias: {} (Duracion: {} ms)", fin, (fin-inicio));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener materia por ID", description = "Devuelve una materia por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Materia encontrada"),
        @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorId(
            @Parameter(description = "ID de la materia", example = "1") @PathVariable Long id) {
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

    @Operation(summary = "Obtener materia por código único", description = "Devuelve una materia por su código único.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Materia encontrada"),
        @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @GetMapping("/codigo/{codigoUnico}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorCodigoUnico(
            @Parameter(description = "Código único de la materia", example = "MAT101") @PathVariable String codigoUnico) {
        MateriaDTO materia = materiaService.obtenerMateriaPorCodigoUnico(codigoUnico);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @Operation(summary = "Crear materia", description = "Crea una nueva materia.")
    @ApiResponse(responseCode = "201", description = "Materia creada")
    @PostMapping
    public ResponseEntity<MateriaDTO> crearMateria(@RequestBody MateriaDTO materia) {
        //MateriaDTO materiaDTO = new MateriaDTO(materia.getId(), materia.getNombre(), materia.getCodigoUnico());
        MateriaDTO nueva = materiaService.crearMateria(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualizar materia", description = "Actualiza los datos de una materia existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Materia actualizada"),
        @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> actualizarMateria(
            @Parameter(description = "ID de la materia", example = "1") @PathVariable Long id,
            @RequestBody MateriaDTO materia) {
        //MateriaDTO materiaDTO = new MateriaDTO(materia.getId(), materia.getNombreMateria(), materia.getCodigoUnico());
        MateriaDTO actualizadaDTO = materiaService.actualizarMateria(id, materia);
        //Materia actualizada = new Materia(actualizadaDTO.getId(), actualizadaDTO.getNombre(), actualizadaDTO.getCodigoUnico());
        return ResponseEntity.ok(actualizadaDTO);
    }

    @Operation(summary = "Eliminar materia", description = "Elimina una materia por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Materia eliminada"),
        @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(
            @Parameter(description = "ID de la materia", example = "1") @PathVariable Long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar círculo de prerequisitos", description = "Verifica si agregar un prerequisito formaría un círculo en la materia.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "No forma círculo"),
        @ApiResponse(responseCode = "400", description = "Formaría círculo o materia no encontrada")
    })
    @GetMapping("/formaria-circulo/{materiaId}/{prerequisitoId}")
    @Transactional
    public ResponseEntity<Boolean> formariaCirculo(
            @Parameter(description = "ID de la materia", example = "1") @PathVariable Long materiaId,
            @Parameter(description = "ID del prerequisito", example = "2") @PathVariable Long prerequisitoId) {
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
