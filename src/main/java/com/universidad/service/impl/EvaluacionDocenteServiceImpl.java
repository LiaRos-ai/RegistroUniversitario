package com.universidad.service.impl;

import com.universidad.model.EvaluacionDocente;
import com.universidad.model.Docente;
import com.universidad.repository.EvaluacionDocenteRepository;
import com.universidad.repository.DocenteRepository;
import com.universidad.service.IEvaluacionDocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionDocenteServiceImpl implements IEvaluacionDocenteService {
    @Autowired
    private EvaluacionDocenteRepository evaluacionDocenteRepository;
    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public EvaluacionDocente crearEvaluacion(EvaluacionDocente evaluacion) {
        Long docenteId = evaluacion.getDocente().getId();

        // Recuperar el docente existente desde la base de datos
        Docente docente = docenteRepository.findById(docenteId)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
    
        // Asociar correctamente
        evaluacion.setDocente(docente);
        return evaluacionDocenteRepository.save(evaluacion);
    }

    @Override
    public List<EvaluacionDocente> obtenerEvaluacionesPorDocente(Long docenteId) {
        Docente docente = docenteRepository.findById(docenteId).orElse(null);
        if (docente == null) return java.util.Collections.emptyList();
        return evaluacionDocenteRepository.findByDocente(docente);
    }

    @Override
    public EvaluacionDocente obtenerEvaluacionPorId(Long id) {
        return evaluacionDocenteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Docente> obtenerTodosConEvaluaciones() {
        return docenteRepository.findAllWithEvaluaciones();
    }
    
    @Override
    public void eliminarEvaluacion(Long id) {
        evaluacionDocenteRepository.deleteById(id);
    }
}
