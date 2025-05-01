package com.universidad.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadesUpdateDTO {
    @NotEmpty(message = "La lista de unidades no puede estar vacía")
    private List<UnidadTematicaDTO> unidades;
}
