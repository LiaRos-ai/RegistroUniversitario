# Optional en Java - Guía Rápida  

## 📌 ¿Qué es Optional?  
Clase contenedora (`java.util.Optional`) introducida en Java 8 para representar un valor que puede ser `null`. Su objetivo es evitar `NullPointerException` y manejar casos nulos de forma explícita.  

## 🎯 ¿Por qué usarlo?  
- **Expresa claridad**: Indica que un valor puede estar ausente.  
- **Evita `null`**: Reduce errores como `NullPointerException`.  
- **API fluida**: Permite operaciones encadenadas (`map`, `filter`, etc.).  

## 🛠️¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?  
| Método                | Descripción                                                                 | Ejemplo                                  |  
|-----------------------|-----------------------------------------------------------------------------|------------------------------------------|  
| `Optional.empty()`    | Retorna un `Optional` vacío.                                               | `Optional.empty()`                       |  
| `Optional.of(valor)`  | Crea un `Optional` con un valor **no nulo** (error si es `null`).          | `Optional.of("Hola")`                    |  
| `Optional.ofNullable(valor)` | Crea un `Optional` que acepta `null`.                              | `Optional.ofNullable(null)`              |  
| `orElse(default)`     | Retorna el valor o un predeterminado.                                      | `opt.orElse("default")`                  |  
| `orElseThrow()`       | Lanza una excepción si no hay valor.                                       | `opt.orElseThrow(() -> new Exception())` |  
| `isPresent()`         | Verifica si hay valor.                                                     | `if (opt.isPresent()) { ... }`           |  

## ✅ ¿Qué ventajas tiene Optional frente a regresar `null`? 
- **Código más seguro**: Evita checks `if (obj != null)`.  
- **Legibilidad**: Métodos como `orElse()` hacen el código más declarativo.  

## 🔄 ¿Cómo se integra Optional en Spring Data JPA?	  
Los repositorios pueden retornar `Optional` en consultas:  
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```
## 🔄 ¿Qué método de Optional permite lanzar una excepción si no hay resultado?	  
El método `orElseThrow()` de `Optional` permite lanzar una excepción si el valor no está presente. Por ejemplo:
```java
Usuario usuario = usuarioRepository.findById(id)
                      .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
```
## 🔄¿Por qué es útil Optional en el contexto de una API REST?
En una API REST, `Optional` permite manejar respuestas en las que un recurso puede no estar presente sin depender de `null`. Se puede utilizar en servicios para evitar respuestas inesperadas y mejorar la robustez de la API.

# Base de Datos: Relación Estudiantes-Materias

## Tabla `estudiante_materia`

Tabla de relación muchos-a-muchos que registra las materias en las que está inscrito cada estudiante.

### Estructura SQL
```sql
CREATE TABLE estudiante_materia (
    id_estudiante BIGINT NOT NULL,
    id_materia BIGINT NOT NULL,
    PRIMARY KEY (id_estudiante, id_materia),
    FOREIGN KEY (id_estudiante) REFERENCES persona(id_persona),
    FOREIGN KEY (id_materia) REFERENCES materias(id_materia)
);
```

### Insercion datos ejemplo
```sql
INSERT INTO estudiante_materia (id_estudiante, id_materia) VALUES
(1, 1), -- Estudiante 1 inscrito en Álgebra
(1, 3), -- Estudiante 1 inscrito en Química Orgánica
(2, 2), -- Estudiante 2 inscrito en Física General
(3, 5), -- Estudiante 3 inscrito en Programación Avanzada
(4, 4), -- Estudiante 4 inscrito en Inglés Técnico
(4, 2); -- Estudiante 4 también inscrito en Física General
```
### Tabla materias
Esta tabla almacena información sobre las materias disponibles.
Datos de ejemplo para materias:

```sql
INSERT INTO estudiante_materia (id_estudiante, id_materia) VALUES
INSERT INTO materias (codigo_unico, creditos, nombre_materia) VALUES
('MAT101', 3, 'Álgebra'),
('PHY202', 4, 'Física General'),
('CHE303', 4, 'Química Orgánica'),
('ENG104', 2, 'Inglés Técnico'),
('CS505', 5, 'Programación Avanzada');
```

### Diagrama de relaciones

```
persona (1) → (n) estudiante_materia (n) ← (1) materias
```