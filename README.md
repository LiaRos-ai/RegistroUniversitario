# Documentación de Modificaciones – Tarea Parte 1

## Relaciones One-to-Many y Cascadas

### Participantes del Grupo
- **Picavia Saravia Julio Erick**
- **Carlos Javier Callizaya Rosas**
- **Mendez Vargas Amiel Natanaeli**
- **Fernandez Flores Adriana Valeria**
- **Aruquipa Ururi Luz Edely**
- **Iver Mamani Cordero**

---

## Cambios y Creaciones Realizadas

### 1. Creación de la Entidad `UnidadTematica` (Realizado por Fernandez Adriana)
Se desarrolló la clase `UnidadTematica.java` con los siguientes campos:
- `id`
- `titulo`
- `descripcion`
- Relación con la entidad `Materia` mediante `@ManyToOne`.

Además, se utilizó la anotación `@JsonBackReference` para evitar ciclos de serialización.

```java
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UnidadTematica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    @JsonBackReference
    private Materia materia;
}
### 2. Modificación de la Entidad `Materia` (Realizado por Fernandez Adriana)

Se incorporó la siguiente lista de unidades temáticas:  
Esta relación permite vincular múltiples unidades temáticas a una sola materia.


@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
private List<UnidadTematica> unidades = new ArrayList<>();

### 3. Creación de los DTOs (Realizado por Fernandez Adriana)
## Se añadieron los siguientes DTOs:

# UnidadTematicaDTO.java
@Data
public class UnidadTematicaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
}

// MateriaDTO.java se amplió para incluir el campo:
private List<UnidadTematicaDTO> unidadesTematicas;

// 4. Creación del repositorio UnidadTematicaRepository (Realizado por Picavia Julio Erick)

public interface UnidadTematicaRepository extends JpaRepository<UnidadTematica, Long> {
    List<UnidadTematica> findByMateriaId(Long materiaId);
}

// 5. Creación de la interfaz y servicio de UnidadTematica (Realizado por Picavia Julio Erick)

// Interfaz IUnidadTematicaService.java
public interface IUnidadTematicaService {
    List<UnidadTematicaDTO> obtenerPorMateria(Long materiaId);
}

// Implementación UnidadTematicaServiceImpl.java
@Service
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {
    // Lógica del método
    @Override
    public List<UnidadTematicaDTO> obtenerPorMateria(Long materiaId) {
        // implementación
    }
}

// 6. Modificación en la interfaz IMateriaService
public interface IMateriaService {
    List<MateriaDTO> listarMateriasConUnidades();
}

// 7. Modificación en MateriaServiceImpl
@Override
@Transactional
public List<MateriaDTO> listarMateriasConUnidades() {
    // implementación del método
}

// 8. Modificación en MateriaController
@GetMapping("/con-unidades")
public List<MateriaDTO> listarMateriasConUnidades() {
    return materiaService.listarMateriasConUnidades();
}

// 9. Creación del controlador UnidadTematicaController (Realizado por Fernandez Adriana)
@GetMapping("/materia/{materiaId}")
public List<UnidadTematicaDTO> listarPorMateria(@PathVariable Long materiaId) {
    return service.obtenerPorMateria(materiaId);
}

// Extras implementados (Realizado por Fernandez Adriana)
// Se cumplió con el requerimiento adicional de listar todas las materias con sus respectivas unidades temáticas utilizando el endpoint:
GET /api/materias/con-unidades

// Parte 2: Actualización en Cascada (CascadeType.ALL)
// Ejercicio 2 – Reemplazo de unidades temáticas en cascada:

// 1. Creación del endpoint PUT /materias/{id}/unidades (Realizado por Mendez Amiel)
// Se implementó un endpoint PUT /api/materias/{id}/unidades que permite reemplazar por completo las unidades temáticas de una materia.
// Esto se hizo utilizando las anotaciones cascade = CascadeType.ALL y orphanRemoval = true, de forma que:
// Las unidades anteriores se eliminan automáticamente.
// Las nuevas unidades se guardan al mismo tiempo que la materia.
// Se evitó guardar duplicados con .distinct().

@PutMapping("/materias/{id}/unidades")
public ResponseEntity<MateriaDTO> reemplazarUnidades(@PathVariable Long id, @RequestBody List<UnidadTematicaDTO> nuevasUnidades) {
    MateriaDTO materiaActualizada = materiaService.reemplazarUnidadesTematicas(id, nuevasUnidades);
    return ResponseEntity.ok(materiaActualizada);
}

// En IMateriaService:
MateriaDTO reemplazarUnidadesTematicas(Long id, List<UnidadTematicaDTO> nuevasUnidades);

// 2. Configuración de la relación con cascade y orphanRemoval (Realizado por Mendez Amiel)
// La relación OneToMany ya estaba definida correctamente así:
@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
private List<UnidadTematica> unidades = new ArrayList<>();

// Esto permite:
// Eliminar automáticamente unidades que se eliminen de la lista (orphanRemoval).
// Guardar nuevas unidades junto con la materia (cascade).

// 3. Lógica de reemplazo en el servicio (Realizado por Mendez Amiel)
// En MateriaServiceImpl.java se creó el método reemplazarUnidadesTematicas(...) que:

@Override
@Transactional
public MateriaDTO reemplazarUnidadesTematicas(Long id, List<UnidadTematicaDTO> nuevasUnidadesDTO) {
    Materia materia = materiaRepository.findById(id).orElseThrow(...);
    materia.getUnidades().clear();
    List<UnidadTematica> nuevasUnidades = nuevasUnidadesDTO.stream().distinct().map(dto -> {
        UnidadTematica unidad = new UnidadTematica();
        unidad.setTitulo(dto.getTitulo());
        unidad.setDescripcion(dto.getDescripcion());
        unidad.setMateria(materia);
        return unidad;
    }).collect(Collectors.toList());
    materia.getUnidades().addAll(nuevasUnidades);
    Materia materiaGuardada = materiaRepository.save(materia);
    return mapper.toDTO(materiaGuardada);
}

// 4. Prueba en Postman (Realizado por Mendez Amiel)
// Se probó el endpoint PUT /materias/1/unidades enviando una nueva lista de unidades temáticas en formato JSON.
// La solicitud reemplazó correctamente las unidades anteriores por las nuevas (Trigonometría y Álgebra Lineal).
// El resultado fue exitoso (200 OK), validando el uso de cascade = ALL y orphanRemoval = true.
