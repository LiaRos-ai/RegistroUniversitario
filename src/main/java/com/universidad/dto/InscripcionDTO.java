package com.universidad.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para la inscripción de un estudiante en una materia")
public class InscripcionDTO {
    @Schema(description = "ID de la inscripción", example = "1")
    private Long id;
    @Schema(description = "ID del estudiante", example = "10")
    private Long estudianteId;
    @Schema(description = "ID de la materia", example = "5")
    private Long materiaId;
    @Schema(description = "Nombre del estudiante", example = "Juan Pérez")
    private String estudianteNombre;
    @Schema(description = "Nombre de la materia", example = "Matemáticas")
    private String materiaNombre;
    @Schema(description = "Fecha de inscripción", example = "2025-05-25")
    private LocalDate fechaInscripcion;
    @Schema(description = "Estado de la inscripción", example = "ACTIVA")
    private String estado;
}
