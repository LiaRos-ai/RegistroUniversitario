package com.universidad.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para Docente")
public class DocenteDTO {
    @Schema(description = "ID del docente", example = "1")
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del docente", example = "Juan")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido del docente", example = "Pérez")
    private String apellido;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    @Schema(description = "Email del docente", example = "juan.perez@universidad.edu")
    private String email;
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    @Schema(description = "Fecha de nacimiento", example = "1980-05-25")
    private LocalDate fechaNacimiento;
    @Schema(description = "Número de empleado", example = "EMP123")
    private String nroEmpleado;
    @Schema(description = "Departamento", example = "Matemáticas")
    private String departamento;
}