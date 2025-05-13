package com.universidad.service;

import com.universidad.dto.DocenteDTO;

import java.util.List;

public interface IDocenteService {

    List<DocenteDTO> obtenerTodosLosDocentes();
    List<DocenteDTO> obtenerTodosLosDocentesSegunDepartamento(String departamento);
}
