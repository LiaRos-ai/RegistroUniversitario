package com.universidad.service.impl;

import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MateriaServiceImpl implements IMateriaService {
    @Autowired
    private MateriaRepository materiaRepository;

    private MateriaDTO convertToDTO(Materia materia){
        return MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .build();
    }
}
