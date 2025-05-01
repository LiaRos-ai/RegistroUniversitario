package com.universidad.controller;

import com.universidad.dto.UnidadTematicaDTO;
import com.universidad.service.IUnidadTematicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/materias")
public class UnidadTematicaController {

    @Autowired
    private IUnidadTematicaService unidadService;

    @GetMapping("/{id}/unidades")
    public List<UnidadTematicaDTO> listarUnidadesPorMateria(@PathVariable Long id) {
        return unidadService.listarPorMateria(id);
    }

}