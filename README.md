<<<<<<< HEAD
// === REGISTRO DE IMPLEMENTACIONES - GRUPO 12 ===
// Tema: Relaciones One-To-Many y Persistencia en Cascada
// Participantes:
// - Ronald Mendoza Caspa
// - Victor Bernardo Quispe Rojas
// - Alvin Angel Magne Aruquipa
// - Blanca Nataly Chipana Orellana
// - Cristian William Bautista Villcacuti
// - Rosa Katerine Gonzales Choque
// - Jose Julián Quinteros Mollinedo
// - Gabriel Omar Cumara Patty

// 1. Definición de la clase UnidadTematica (por Rosa Gonzales)
// Se creó una entidad llamada UnidadTematica, la cual representa los temas abordados dentro de una materia. 
// Esta entidad está enlazada con Materia usando @ManyToOne y se usa @JsonBackReference para prevenir ciclos de referencia circular al serializar.

package com.universidad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

// 2. Se amplió la clase Materia para incluir las unidades temáticas relacionadas (por Cristian Bautista)
// La entidad Materia incorpora una lista de UnidadTematica mediante OneToMany.
// CascadeType.ALL asegura persistencia automática de las unidades y orphanRemoval garantiza que al eliminar una unidad, se borre del sistema.

@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
private List<UnidadTematica> unidades = new ArrayList<>();

// 3. Se definieron los DTOs correspondientes (por Alvin Magne)
// Se agregaron los DTOs necesarios para mapear los datos entre entidad y capa de presentación:

// UnidadTematicaDTO.java
@Data
public class UnidadTematicaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
}

// En MateriaDTO.java se añadió el campo:
private List<UnidadTematicaDTO> unidadesTematicas;

// 4. Repositorio de UnidadTematica (por Victor Quispe)
// Se generó una interfaz repositorio para facilitar operaciones con UnidadTematica.

public interface UnidadTematicaRepository extends JpaRepository<UnidadTematica, Long> {
    List<UnidadTematica> findByMateriaId(Long materiaId);
}

// 5. Interfaz y servicio para UnidadTematica (por Ronald Mendoza)

// IUnidadTematicaService.java
public interface IUnidadTematicaService {
    List<UnidadTematicaDTO> listarPorMateria(Long materiaId);
}

// UnidadTematicaServiceImpl.java
@Service
public class UnidadTematicaServiceImpl implements IUnidadTematicaService {

    @Autowired
    private UnidadTematicaRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UnidadTematicaDTO> listarPorMateria(Long materiaId) {
        return repository.findByMateriaId(materiaId)
                         .stream()
                         .map(unidad -> mapper.map(unidad, UnidadTematicaDTO.class))
                         .toList();
    }
}

// 6. Se amplió la interfaz IMateriaService (por Blanca Chipana)
public interface IMateriaService {
    List<MateriaDTO> obtenerMateriasConUnidades();
}

// 7. Implementación en MateriaServiceImpl (por Gabriel Cumara)
@Override
@Transactional
public List<MateriaDTO> obtenerMateriasConUnidades() {
    return materiaRepository.findAll().stream()
        .map(m -> mapper.toDTO(m))
        .toList();
}

// 8. Controlador de Materia - nuevo endpoint (por Jose Quinteros)
@GetMapping("/con-unidades")
public List<MateriaDTO> obtenerMateriasConUnidades() {
    return materiaService.obtenerMateriasConUnidades();
}

// 9. Controlador de UnidadTematica (por Gabriel Cumara)
@GetMapping("/materia/{materiaId}")
public List<UnidadTematicaDTO> listarPorMateria(@PathVariable Long materiaId) {
    return unidadTematicaService.listarPorMateria(materiaId);
}

// === SEGUNDA PARTE: ACTUALIZACIÓN EN CASCADA ===
// Ejercicio: Sustituir por completo las unidades temáticas asociadas a una materia

// 1. Endpoint PUT para reemplazar unidades temáticas de una materia (por Amiel Mendez)

@PutMapping("/materias/{id}/unidades")
public ResponseEntity<MateriaDTO> actualizarUnidades(@PathVariable Long id, @RequestBody List<UnidadTematicaDTO> nuevasUnidades) {
    MateriaDTO actualizada = materiaService.reemplazarUnidadesTematicas(id, nuevasUnidades);
    return ResponseEntity.ok(actualizada);
}

// En la interfaz IMateriaService:
MateriaDTO reemplazarUnidadesTematicas(Long id, List<UnidadTematicaDTO> unidadesActualizadas);

// 2. La configuración de la relación en la clase Materia ya contenía:
// CascadeType.ALL y orphanRemoval habilitados para manejar correctamente el reemplazo.

@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
private List<UnidadTematica> unidades = new ArrayList<>();

// 3. Lógica para el reemplazo de unidades (por Amiel Mendez)

@Override
@Transactional
public MateriaDTO reemplazarUnidadesTematicas(Long id, List<UnidadTematicaDTO> nuevasUnidadesDTO) {
    Materia materia = materiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Materia no encontrada"));

    // Se eliminan las unidades actuales
    materia.getUnidades().clear();

    // Se agregan las nuevas unidades temáticas
    List<UnidadTematica> nuevasUnidades = nuevasUnidadesDTO.stream()
        .distinct()
        .map(dto -> {
            UnidadTematica unidad = new UnidadTematica();
            unidad.setTitulo(dto.getTitulo());
            unidad.setDescripcion(dto.getDescripcion());
            unidad.setMateria(materia);
            return unidad;
        }).collect(Collectors.toList());

    materia.getUnidades().addAll(nuevasUnidades);

    // Guardar cambios
    Materia actualizada = materiaRepository.save(materia);
    return mapper.toDTO(actualizada);
}

// 4. Pruebas realizadas con Postman (por todo el equipo)
// Se envió una petición PUT a: /api/materias/1/unidades
// con una lista de objetos JSON representando nuevas unidades temáticas.
// Resultado: se reemplazaron exitosamente las anteriores y se verificó persistencia con cascade y eliminación con orphanRemoval.
=======
2025# Proyecto CRUD - Universidad

Este repositorio contiene la base del proyecto Universitario utilizando **Spring Boot**. Cada grupo debe trabajar en su propia rama según las instrucciones del docente.

---

## 🚀 Objetivo

Completar las operaciones requeridas sobre el Proyecto.

**Grupos y ramas asignadas**

|**Grupo**|**Ramaogiana**|**Integrantes**|
| :-: | :-: | :-: |
|01|grupo-01-springb|[Enrique Rafael Fernández Chiri, Nohemy Ruth Mamani Apaza, Samantha Rousse Gironda Mina, Josué Misael López Huanca, Luz Belén Chávez Patzi, Oscar Joel Choque Flores, Cristhian Pablo Álvarez Guarachi]|
|02|grupo-02-springb|[Maya Cádiz, Leandro Chávez, Masiel Chirinos, Sergio Luque, Jordy Miranda, Saúl Sánchez, Elizabeth Suzaño]|
|03|grupo-03-springb|[Bautista Mollo Denzel Guden, Copa Quispe Esther Sara, Guarachi Arguata Alberth, Reyes Barja Carlos Eduardo, Rojas Condoria Fidel Angel, Tancara Suñagua Joel Hernan.]|
|04|grupo-04-springb|[Marcelo Alejandro Villarroel Gutiérrez, Jonathan Gerson Gutiérrez Condori, Betzabe Gutiérrez Morales, Mikaela Belén Córdova Vásquez, Jhessica Coral Villca Palma, Karen Rocio Catari Calderón, Abigail Blanca Mamani Mamani]|
|05|grupo-05-springb|["Diana Cecilia Flores Chacón, Juan Sebastián Camacho Fernández, Andrés Wiliam Galarza Salguero, Harold Ruddy Quispe Hilari, José Alejandro Díaz Ali, Michelle Ruth Poma Ramos, Daron Augusto Baldiviezo Aillon"]|
|06|grupo-06-springb|[José Aruquipa, Miguel Calderón, Herlan Callisaya, Oscar Luján, Edith Marca, Luz Tinta, Daniel Zeballos]|
|07|grupo-07-springb|[Lenz Abad Alanoca Ojeda,Juan Vidal Mamani Riveros,Herlan Choque Flores,Lorgio Emilio Chura Carrillo,Jesús Alejandro Cruz,Juan Carlos Limachi Maydana]|
|08|grupo-08-springb|[Amílcar Josías Yujra Chipana, Luis Alfredo Quispe Ortiz, Alan Sergio Yupanqui Corini, Yehonatan Oscar Limachi Corina, Melany Abril Mamani Chamizo, Limbert Mamani Quiñajo, Ronald Choque Sillo]|
|09|grupo-09-springb|[Bautista Coaquira Jose Abraham, Laura Rios Lizbeth Fabiola, Penélope Gema Copana Fuentes, Sasha Johannes konrad Arana Ramirez, Callisaya Vargas Marco Ronaldo, Callisaya Lanes Shelly Anahi, Choque Gutiérrez Manuel Alejandro, Elías Daniel Beltrán Selaez]|
|10|grupo-10-springb|[María Teresa Aspiazu Sánchez, Jesús Abed Herrera Sirpa, Joel Alejandro Pérez Murillo, Ariadne Checcid Quiroz Coila, Brandom Jhoseff Amezaga Garrido Cael Mathew Cuevas Alconini, José Alfredo Choque Choque]|
|11|grupo-11-springb|[Israel Andrés Quenta Pomacusi, Edson Javier Mamani Ticona,Jhamil Elías Mamani Colque,Alexander Nataniel Castillo Centellas,Adrián Marcelo Requena Oros,Maritza Zárate Paco ,Jhoel Alexander Chipana Paye]|
|12|grupo-12-springb|[Víctor Bernardo Quispe Rojas,Gabriel Omar Cumara Patty,Cristian William Bautista Villcacuti,Rosa Katerine Gonzales Choque,Alvin Angel Magne Aruquipa,Blanca Nataly Chipana Orellana,Ronald Mendoza Caspa,José Julián Quinteros Mollinedo]|
|13|grupo-13-springb|[Quispe Adriana, Carvajal Ester, Tirado Nayheli, Canaviri Carlos, Loza Humberto, Mamani Sarahi, Ticona Alex]|
|14|grupo-14-springb|[Gutiérrez Challapa Daniel Rodrigo, Hidalgo Colque Ariana Daniela,Huanca Tito José Manuel,Mamani Mamani Mirko Sony,Quecaño Uruña Erika,Quiñajo Berrios Melina Viana]|
|15|grupo-15-springb|[Julio Picavia Saravia, Carlos Callisaya Rosas, Iver Mamani, Amiel Natanieli Méndez Vargas, Adriana Valeria Fernández Flores, Luz Edely Aruquipa Ururi]|
|16|Grupo-16-springb|[Miranda Aguirre Carlos Manuel, Tapia Cortez Genesis Jalid, Aarón Oswaldo Nina Calzada, Lucas Calderon, David Mamani, Rudy Ibarra, Julio Cesar Ticona, José Alejandro Fernández Sánchez ]|




---


## 📦 Cómo trabajar en tu rama

1. Clona el repositorio:
```bash
git clone https://github.com/LiaRos-ai/RegistroUniversitario.git
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
