package com.universidad.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materia") // Nombre de la tabla en la base de datos
// Esta clase representa una materia en el sistema de gestión de estudiantes
public class Materia {
    
    @Id // Anotación que indica que este campo es la clave primaria
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "id_materia") // Nombre de la columna en la base de datos
    // El ID de la materia es generado automáticamente por la base de datos
    private Long id;

    @Column(name = "nombre_materia", nullable = false, length = 100) // Columna no nula con longitud máxima de 100 caracteres
    // El nombre de la materia no puede ser nulo y tiene una longitud máxima de 100 caracteres
    private String nombreMateria;

    @Column(name = "codigo_unico", nullable = false, unique = true) // Columna no nula y con valor único
    // El código único de la materia no puede ser nulo y debe ser único en la base de datos
    private String codigoUnico;

    @Column(name = "creditos", nullable = false) // Columna no nula
    // El número de créditos de la materia no puede ser nulo
    private Integer creditos;

}