# Grupo 4 - Proyecto Gestión Universidad

## Parte 1: Relaciones (One-to-Many) + Referencias circulares

### Ejercicio 1 – Relación One-to-Many (Materia → UnidadTematica)

#### 1. Crear las entidades Materia y UnidadTematica
En esta primera parte del ejercicio, trabajamos en la creación de las entidades **`Materia`** y **`UnidadTematica`** en el sistema. Utilizamos **JPA** para definir estas entidades y sus relaciones en la base de datos.

- **`Materia`** representa las materias del sistema (por ejemplo, **Álgebra**, **Geometría**, etc.).
- **`UnidadTematica`** representa las unidades temáticas dentro de cada materia (por ejemplo, **Unidad 1: Álgebra básica**, **Unidad 2: Geometría avanzada**).

Creamos una relación **One-to-Many** entre **`Materia`** y **`UnidadTematica`**. Esto significa que una **Materia** puede tener varias **UnidadTematica** asociadas.

**Código en `Materia.java`**:
```java
@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@JsonManagedReference // Evita el ciclo infinito
private List<UnidadTematica> unidadesTematicas = new ArrayList<>();
```

En UnidadTematica, utilizamos @ManyToOne para referenciar la Materia asociada a cada unidad, completando así la relación bidireccional.

**Código en `UnidadTematica.java`**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "materia_id", nullable = false)
@JsonBackReference // Evita el ciclo infinito
private Materia materia;
```

#### 2. Una materia puede tener muchas unidades temáticas
Implementamos correctamente esta relación, donde una Materia puede tener muchas UnidadTematica, y cada unidad está asociada a una única materia. Esto nos permitió manejar adecuadamente los datos en las relaciones entre entidades.

#### 3. Mostrar en un endpoint todas las unidades temáticas de una materia
Creamos un endpoint GET /materias/{id}/unidades para mostrar todas las unidades temáticas asociadas a una materia específica. Este endpoint recibe el ID de la materia y devuelve todas las unidades temáticas correspondientes.

**Código en `MateriaController.java`**:
```java
@GetMapping("/{id}/unidades")
public ResponseEntity<List<UnidadTematicaDTO>> obtenerUnidadesPorMateria(@PathVariable Long id) {
    List<UnidadTematicaDTO> unidades = materiaService.obtenerUnidadesPorMateria(id);
    return unidades.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(unidades);
}
```

#### 4. Usar @JsonManagedReference y @JsonBackReference para evitar bucles infinitos
Dado que la relación entre Materia y UnidadTematica es bidireccional, evitamos bucles infinitos al serializar las entidades utilizando @JsonManagedReference en Materia y @JsonBackReference en UnidadTematica.

- **@JsonManagedReference**: Se coloca en el lado "uno" de la relación (en Materia) para indicar que esta es la parte gestionada (será serializada).
- **@JsonBackReference**: Se coloca en el lado "muchos" de la relación (en UnidadTematica) para evitar que se genere una referencia recursiva infinita.

#### Extra: Listar todas las materias con sus unidades incluidas
Para la parte extra del ejercicio, implementamos un endpoint adicional GET /materias que lista todas las materias junto con sus unidades temáticas asociadas.

**Código en `MateriaController.java`**:
```java
@GetMapping
public ResponseEntity<List<MateriaDTO>> obtenerTodasLasMaterias() {
    List<MateriaDTO> result = materiaService.obtenerTodasLasMaterias();
    return ResponseEntity.ok(result);
}
```

Este endpoint recupera todas las materias y las unidades temáticas relacionadas, devolviendo un conjunto completo de información en una única respuesta.

![image](https://github.com/user-attachments/assets/4b449703-c858-4289-9738-976ad23710da)


## Parte 2: Actualización en Cascada (CascadeType.ALL)

### Ejercicio 2 – Reemplazo de unidades temáticas en cascada

#### 1. Implementar un endpoint PUT `/materias/{id}/unidades`:
En esta segunda parte del ejercicio, implementamos un endpoint **`PUT /materias/{id}/unidades`** que permite reemplazar completamente la lista de unidades temáticas asociadas a una materia específica. Este endpoint recibe un conjunto de nuevas unidades temáticas en formato JSON y las reemplaza por las existentes.

**Código en `MateriaController.java`**:
```java
@PutMapping("/{id}/unidades")
public ResponseEntity<MateriaDTO> actualizarUnidadesPorMateria(@PathVariable Long id, @RequestBody List<UnidadTematicaDTO> nuevasUnidades) {
    MateriaDTO materiaDTO = materiaService.actualizarUnidadesPorMateria(id, nuevasUnidades);
    return ResponseEntity.ok(materiaDTO);
}
```

Este endpoint:
- Recibe el ID de la materia a través del PathVariable.
- La lista de nuevas unidades temáticas se pasa en el cuerpo de la solicitud como @RequestBody.
- Se llama al método actualizarUnidadesPorMateria del servicio para manejar la lógica de reemplazo de unidades temáticas.

#### 2. Reemplazar completamente la lista de unidades temáticas de una materia:
Para realizar el reemplazo completo de las unidades temáticas, primero eliminamos todas las unidades existentes asociadas a la materia mediante el uso de materia.getUnidadesTematicas().clear().

Luego, se agregan las nuevas unidades temáticas enviadas en la solicitud. Cada unidad se asocia a la materia correspondiente.

**Código en `MateriaServiceImpl.java`**:
```java
@Override
public MateriaDTO actualizarUnidadesPorMateria(Long id, List<UnidadTematicaDTO> nuevasUnidades) {
    Materia materia = materiaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));

    // Limpiar las unidades temáticas actuales (esto se maneja con orphanRemoval = true)
    materia.getUnidadesTematicas().clear();

    // Agregar las nuevas unidades temáticas
    for (UnidadTematicaDTO unidadDTO : nuevasUnidades) {
        UnidadTematica nuevaUnidad = new UnidadTematica();
        nuevaUnidad.setTema(unidadDTO.getTema());
        nuevaUnidad.setDescripcion(unidadDTO.getDescripcion());
        nuevaUnidad.setMateria(materia);  // Relacionamos la nueva unidad con la materia
        materia.addUnidadTematica(nuevaUnidad);  // Usamos el método helper para agregar la unidad
    }

    // Guardamos la materia con las nuevas unidades
    materiaRepository.save(materia);

    return mapToDTO(materia);
}
```

#### 3. Usar cascade = CascadeType.ALL y orphanRemoval = true:
Para asegurarnos de que las operaciones realizadas sobre Materia afecten correctamente a las UnidadTematica asociadas, usamos CascadeType.ALL en la relación One-to-Many de Materia y UnidadTematica.

orphanRemoval = true garantiza que las unidades temáticas que ya no están asociadas a la materia se eliminen automáticamente de la base de datos cuando se eliminen de la lista.

**Código en `Materia.java`**:
```java
@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
@JsonManagedReference
private List<UnidadTematica> unidadesTematicas = new ArrayList<>();
```

#### 4. Probar que se eliminan las unidades antiguas y se agregan nuevas:
Con la configuración de cascade = CascadeType.ALL y orphanRemoval = true, cada vez que se hace un reemplazo completo de las unidades temáticas de una materia, las antiguas unidades se eliminan y las nuevas unidades se agregan correctamente.

Esto se gestiona internamente mediante la limpieza de la lista de unidades existentes materia.getUnidadesTematicas().clear(), seguida de la inserción de las nuevas unidades.

#### 5. Validaciones para evitar duplicados:
Antes de agregar nuevas unidades temáticas, implementamos una validación para evitar duplicados. Si una unidad con el mismo tema ya existe en la lista de unidades temáticas de la materia, no se agrega.

**Código en `MateriaServiceImpl.java`**:
```java
for (UnidadTematicaDTO unidadDTO : nuevasUnidades) {
    // Verificar si ya existe una unidad con el mismo tema
    boolean existeUnidad = materia.getUnidadesTematicas().stream()
            .anyMatch(u -> u.getTema().equals(unidadDTO.getTema()));
    if (!existeUnidad) {
        UnidadTematica nuevaUnidad = new UnidadTematica();
        nuevaUnidad.setTema(unidadDTO.getTema());
        nuevaUnidad.setDescripcion(unidadDTO.getDescripcion());
        nuevaUnidad.setMateria(materia);  // Relacionamos la nueva unidad con la materia
        materia.addUnidadTematica(nuevaUnidad);
    }
}
```

![image](https://github.com/user-attachments/assets/f7183796-174f-4832-9279-341de443ae63)

![image](https://github.com/user-attachments/assets/fff36787-0e6a-478d-a7df-7bd81359cc51)

