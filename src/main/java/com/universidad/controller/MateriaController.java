package com.universidad.controller;


import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.dto.UnidadesUpdateDTO;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;

import com.universidad.validation.ApiError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import com.universidad.dto.MateriaDTO;
import jakarta.validation.Valid;

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


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private MateriaRepository materiaRepository;



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

    @GetMapping("/formaria-circulo/{materiaId}/{prerequisitoId}") // Endpoint para verificar si una materia formaría un círculo con un prerequisito
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
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

    // En tu MateriaController.java, agrega este método:

    @GetMapping("/{id}/unidades")
    public ResponseEntity<?> obtenerUnidadesDeMateria(@PathVariable Long id) {
        try {
            MateriaDTO materia = materiaService.obtenerMateriaPorId(id);
            if (materia == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(materia.getUnidadesTematicas());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiError(
                            500,
                            "Error al obtener unidades",
                            e.getMessage(),
                            LocalDateTime.now()
                    )
            );
        }
    }

    @PutMapping("/{id}/unidades")
    @Transactional
    public ResponseEntity<?> reemplazarUnidades(
            @PathVariable Long id,
            @Valid @RequestBody UnidadesUpdateDTO unidadesUpdateDTO) {

        try {
            Materia materia = materiaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Materia no encontrada"));

            // Validar duplicados (Extra)
            validarDuplicados(unidadesUpdateDTO.getUnidades());

            // Convertir DTOs a entidades
            List<UnidadTematica> nuevasUnidades = unidadesUpdateDTO.getUnidades().stream()
                    .map(dto -> {
                        UnidadTematica unidad = new UnidadTematica();
                        unidad.setNombre(dto.getNombre());
                        unidad.setDescripcion(dto.getDescripcion());
                        unidad.setOrden(dto.getOrden());
                        return unidad;
                    }).collect(Collectors.toList());

            // Esto activará la cascada y orphanRemoval
            materia.setUnidadesTematicas(nuevasUnidades);

            // No es necesario con @Transactional pero lo dejamos por claridad
            Materia materiaActualizada = materiaRepository.save(materia);

            return ResponseEntity.ok(materiaService.mapToDTO(materiaActualizada));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ApiError(400, "Error de validación", e.getMessage(), LocalDateTime.now())
            );
        }
    }

    // Método para validar duplicados (Extra)
    private void validarDuplicados(List<UnidadTematicaDTO> unidades) {
        Set<String> nombresUnidades = new HashSet<>();
        Set<Integer> ordenes = new HashSet<>();

        for (UnidadTematicaDTO unidad : unidades) {
            if (!nombresUnidades.add(unidad.getNombre().toLowerCase())) {
                throw new IllegalArgumentException("Nombre duplicado: " + unidad.getNombre());
            }
            if (!ordenes.add(unidad.getOrden())) {
                throw new IllegalArgumentException("Orden duplicado: " + unidad.getOrden());
            }
        }
    }


}
