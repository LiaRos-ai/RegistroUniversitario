# Relaciones y Cascadas con JPA (Activida #5)

Nota: *Todos los integrantes colaboraron en la actividad :* 
Parte 1: Relaciones y Cascadas con JPA 
Parte 2: Actualización en Cascada (CascadeType.ALL)

## 📚 Gestión de Unidades Temáticas por Materia

### 🧩 Parte 1: Nuevas Entidades y Funcionalidades

#### ✅ Entidad añadida: UnidadTematica.java
- Campos:
    - id
    - nombre
    - descripcion
- Relaciones:
    - @ManyToOne hacia Materia
    - Anotación: @JsonBackReference

#### 🔧 Entidad modificada: Materia.java
java
@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL)
@JsonManagedReference
private List<UnidadTematica> unidades;


#### 📦 DTOs
- *Nuevo:* UnidadTematicaDTO.java con campos:
    - id
    - nombre
    - descripcion
- *Modificado:* MateriaDTO.java
  java
  private List<UnidadTematicaDTO> unidades;


#### 🗃 Repositorio: UnidadTematicaRepository.java
java
List<UnidadTematica> findByMateriaId(Long materiaId);


#### 🧠 Servicio

- **Interfaz (IMateriaService.java)**
  java
  List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId);


- **Implementación (MateriaServiceImpl.java)**
    - Método nuevo:
      java
      public List<UnidadTematicaDTO> obtenerUnidadesPorMateria(Long materiaId) { ... }

    - Método modificado:
      java
      private MateriaDTO mapToDTO(Materia materia) { ... }

      → se añade .unidades(...)
    - Método nuevo:
      java
      private UnidadTematicaDTO mapToDTO(UnidadTematica unidad) { ... }


#### 🌐 Controlador: MateriaController.java
- Endpoint nuevo:
  java
  @GetMapping("/{id}/unidades")
  public ResponseEntity<List<UnidadTematicaDTO>> listarUnidades(@PathVariable Long id) { ... }

- Endpoint extra:
  java
  @GetMapping("/con-unidades")
  public ResponseEntity<List<MateriaDTO>> obtenerTodasConUnidades() { ... }


---

### 🛠 Parte 2: Mejoras en Persistencia y Eliminación

#### 🔁 Modificación en Materia.java
java
private List<UnidadTematica> unidades = new ArrayList<>();

@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
@JsonManagedReference


#### 🧹 Reemplazo de unidades sin romper referencia
Método en MateriaServiceImpl.java:
java
public void reemplazarUnidadesDeMateria(Long materiaId, List<UnidadTematicaDTO> nuevasUnidades) { ... }

Cambio aplicado:
java
materia.getUnidades().clear();
materia.getUnidades().add(...);

Esto evita el uso de setUnidades(...) y permite que orphanRemoval = true elimine correctamente las unidades huérfanas.

---

### ✅ Beneficios
- Soporte completo para listar, obtener y reemplazar unidades temáticas de cada materia.
- Relaciones JSON gestionadas adecuadamente con @JsonManagedReference y @JsonBackReference.
- Eliminación automática de unidades al actualizar la lista.
- Mejora en la integridad de la persistencia con orphanRemoval = true.