package com.universidad.controller;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.service.IUnidadTematicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones relacionadas con Unidades Temáticas.
 */
@RestController
@RequestMapping("/api/unidades") // prefijo base para todas las unidades
@RequiredArgsConstructor
public class UnidadTematicaController {

    private final IUnidadTematicaService service;

    /**
     * Endpoint que lista todas las unidades temáticas asociadas a una materia específica.
     *
     * @param materiaId ID de la materia
     * @return Lista de unidades temáticas
     */
    @GetMapping("/materia/{materiaId}")
    public List<UnidadTematicaDTO> listarPorMateria(@PathVariable Long materiaId) {
        return service.obtenerPorMateria(materiaId);
    }

    // TAREA GRUPO
}
