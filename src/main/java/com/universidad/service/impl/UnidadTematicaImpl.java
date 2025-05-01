package com.universidad.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.dto.MateriaDTO;
import com.universidad.model.Docente;
import com.universidad.model.Materia;
import com.universidad.model.UnidadTematica;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IUnidadTematicaService;

@Service
public class UnidadTematicaImpl implements IUnidadTematicaService {
    @Autowired
    private UnidadTematicaRepository evaluacionDocenteRepository;
    @Autowired
    private MateriaRepository docenteRepository;

    @Override
    public UnidadTematica crearUnidadTematica(UnidadTematica evaluacion) {
        String materiaID = evaluacion.getMateria().getCodigoUnico();

        // Recuperar el docente existente desde la base de datos
        Materia materia = docenteRepository.findByCodigoUnico(materiaID);
        if (materia == null) {
            throw new RuntimeException("Materia no encontrada");
        }

        // Asociar correctamente
        evaluacion.setMateria(materia);
        return evaluacionDocenteRepository.save(evaluacion);
    }

    @Override
    public List<UnidadTematica> obtenerUnidadTematicaPorMateria(Long materiaID) {
        Materia materia = docenteRepository.findById(materiaID).orElse(null);
        if (materia == null)
            return java.util.Collections.emptyList();
        return evaluacionDocenteRepository.findByMateria(materia);
    }

    @Override
    public List<Materia> obtenerTodaUnidadTematica() {
        return docenteRepository.findAllWithUnidadTematica();
    }

    @Override
    public UnidadTematica obtenerUnidadTematicaPorID(Long id) {
        return evaluacionDocenteRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarUnidadTematica(Long id) {
        evaluacionDocenteRepository.deleteById(id);
    }

    @Override
    public UnidadTematica actualizarUnidadTematica(Long id, UnidadTematica unidadTematica) {
        UnidadTematica unidadTematicaExistente = evaluacionDocenteRepository.findById(id).orElse(null);
        unidadTematicaExistente.setTitulo(unidadTematica.getTitulo());
        unidadTematicaExistente.setDescripcion(unidadTematica.getDescripcion());
        return evaluacionDocenteRepository.save(unidadTematicaExistente);
    }
}
