package com.universidad.controller;

import com.universidad.model.UnidadTematica;
import com.universidad.service.IUnidadTematicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias") // El endpoint base es /materias
public class UnidadTematicaController {

    @Autowired
    private IUnidadTematicaService unidadTematicaService;

    // Endpoint para obtener todas las unidades temáticas de una materia
    @GetMapping("/{id}/unidades") // /materias/{id}/unidades
    public List<UnidadTematica> obtenerUnidadesDeMateria(@PathVariable Long id) {
        // Llama al servicio para obtener las unidades temáticas de la materia con el ID dado
        return unidadTematicaService.obtenerUnidadesPorMateria(id);
    }
}
