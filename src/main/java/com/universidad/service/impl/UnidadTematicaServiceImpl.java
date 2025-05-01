package com.universidad.service.impl;

import com.universidad.model.UnidadTematica;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IUnidadTematicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {

    @Autowired
    private UnidadTematicaRepository unidadTematicaRepository;

    @Override
    public List<UnidadTematica> obtenerUnidadesPorMateria(Long materiaId) {
        return unidadTematicaRepository.findByMateria_Id(materiaId);
    }
}
