/* package com.universidad.service.impl;


import com.universidad.dto.EstudianteDTO;
import com.universidad.model.Docente;
import com.universidad.dto.DocenteDTO;
import com.universidad.repository.DocenteRepository;
import com.universidad.service.IDocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteServiceImpl implements IDocenteService {
    @Autowired
    private DocenteRepository docenteRepository;


    @Override
    public List<DocenteDTO> obtenerTodosLosDocentes(){
        return docenteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

   /*  @Override
    public List<DocenteDTO> obtenerTodosLosDocentesSegunDepartamento(String departamento){
        return docenteRepository.findByDepartamento(departamento).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
 
    private DocenteDTO convertToDTO(Docente docente){
        return DocenteDTO.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .email(docente.getEmail())
                .fechaNacimiento(docente.getFechaNacimiento())
                .nroEmpleado(docente.getNroEmpleado())
                .departmento(docente.getDepartamento())
                .build();
    }
    private Docente convertToEntity(DocenteDTO docente){
        return Docente.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .email(docente.getEmail())
                .fechaNacimiento(docente.getFechaNacimiento())
                .nroEmpleado(docente.getNroEmpleado())
                .departamento(docente.getDepartmento())
                .build();
    }
}
 */