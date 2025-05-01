package com.universidad.service.impl;

import com.universidad.model.UnidadTematica;
import com.universidad.repository.UnidadTematicaRepository;
import com.universidad.service.IUnidadTematicaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {

    private final UnidadTematicaRepository unidadTematicaRepository;

    public UnidadTematicaServiceImpl(UnidadTematicaRepository unidadTematicaRepository) {
        this.unidadTematicaRepository = unidadTematicaRepository;
    }

    @Override
    public List<UnidadTematica> listarPorMateria(Long idMateria) {
        return unidadTematicaRepository.findByMateriaId(idMateria);
    }
}
