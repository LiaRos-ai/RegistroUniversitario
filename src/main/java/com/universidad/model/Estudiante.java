package com.universidad.model; // Define el paquete al que pertenece esta clase

import lombok.AllArgsConstructor; // Importa la anotación AllArgsConstructor de Lombok para generar un constructor con todos los argumentos
import lombok.Data; // Importa la anotación Data de Lombok para generar getters, setters, toString, equals y hashCode
import lombok.EqualsAndHashCode; // Importa la anotación EqualsAndHashCode de Lombok para generar métodos equals y hashCode
import lombok.NoArgsConstructor; // Importa la anotación NoArgsConstructor de Lombok para generar un constructor sin argumentos
import lombok.experimental.SuperBuilder; // Importa la anotación SuperBuilder de Lombok para generar un builder que soporta herencia

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*; // Importa las anotaciones de JPA para la persistencia de datos

@Data // Genera getters, setters, toString, equals y hashCode
@EqualsAndHashCode(callSuper = true) // Genera métodos equals y hashCode, incluyendo los campos de la clase padre
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@SuperBuilder // Genera un builder que soporta herencia
@Entity // Anotación que indica que esta clase es una entidad JPA
@Table(name = "estudiante") // Nombre de la tabla en la base de datos
public class Estudiante extends Persona { // Define la clase Estudiante que extiende de Persona
    
    @Column(name = "numero_inscripcion", nullable = false, unique = true) // Columna no nula y con valor único
    private String numeroInscripcion; // Campo para almacenar el número de inscripción del estudiante

    @Column(name = "estado") // Columna opcional) 
    private String estado; // Campo para almacenar el estado del estudiante (activo, inactivo, etc.)

    @Column(name = "usuario_alta") 
    // El campo usuarioAlta almacena el usuario que dio de alta al estudiante
    //@Basic(optional = false) // Columna no nula
    private String usuarioAlta; // Campo para almacenar el usuario que dio de alta al estudiante
    
    @Column(name = "fecha_alta") // Columna opcional
    @Temporal(TemporalType.DATE) // Tipo de dato fecha
    @Basic(optional = true) // Columna opcional
    // El campo fechaAlta almacena la fecha de alta del estudiante
    private LocalDate fechaAlta; // Campo para almacenar la fecha de alta del estudiante
    
    @Column(name = "usuario_modificacion") // Columna opcional
    private String usuarioModificacion; // Campo para almacenar el usuario que modificó al estudiante

    @Column(name = "fecha_modificacion") // Columna opcional
    @Temporal(TemporalType.DATE) // Tipo de dato fecha
    @Basic(optional = true) // Columna opcional
    // El campo fechaModificacion almacena la fecha de modificación del estudiante
    private LocalDate fechaModificacion; // Campo para almacenar la fecha de modificación del estudiante
    
    @Column(name = "usuario_baja") // Columna opcional
    private String usuarioBaja; // Campo para almacenar el usuario que dio de baja al estudiante
    
    @Column(name = "fecha_baja") // Columna opcional
    @Temporal(TemporalType.DATE) // Tipo de dato fecha
    @Basic(optional = true) // Columna opcional
    // El campo fechaBaja almacena la fecha de baja del estudiante
    private LocalDate fechaBaja; // Campo para almacenar la fecha de baja del estudiante

    @Column(name = "motivo_baja") // Columna opcional
    private String motivoBaja; // Columna opcional para almacenar el motivo de baja del estudiante

    @ManyToMany(fetch = FetchType.LAZY) // Relación muchos a muchos con la entidad Materia
    @JoinTable(name = "estudiante_materia", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_estudiante"), // Columna que referencia al estudiante
            inverseJoinColumns = @JoinColumn(name = "id_materia")  // Columna que referencia a la materia 
    )
    private List<Materia> materias; // Lista de materias asociadas al estudiante
     

}