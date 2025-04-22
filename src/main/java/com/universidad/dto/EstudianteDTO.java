package com.universidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNacimiento;
    private String numeroInscripcion;
    private String estado;
    private String usuarioAlta;
    private String usuarioModificacion;
    private LocalDate fechaAlta;
    private LocalDate fechaModificacion;
    private String usuarioBaja;
    private LocalDate fechaBaja;
    private String motivoBaja;
    
}