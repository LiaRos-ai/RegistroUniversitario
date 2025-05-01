# Proyecto - Uso de Optional en Java con Spring Boot

## INVESTIGACION:
- Pregunta 1 = Alan Sergio Yupanqui Corani
- Pregunta 2 = Melany Abril Mamani Chamizo
- Pregunta 3 = Limbert Mamani Quiñajo
- Pregunta 4 = Luis Alfredo Quispe Ortiz
- Pregunta 5 = Yehonatan Oscar Limachi Corina
- Pregunta 6 = José Alejandro Fernandez Sanchez
## CODIGO:
- Ronald Choque Sillo = Implemento el metodo obtenerMateriasPorEstudiante y ruta en el Controller
- Luis Alfredo Quispe Ortiz y Amilcar Josias Yujra Chipana = Implementaron el metodo obtenerMateriasPorEstudiante en el Service, IEstudianteService y EstudianteServiceImpl
- Amilcar Josias Yujra Chipana = Implemento la carpeta exception, con GlobalExceptionHandler.java para el manejo de excepciones por el Optional y orElseThrow

---

## Parte 1: Investigación

Antes de codificar, se investigó y respondió brevemente a las siguientes preguntas sobre el uso de `Optional` en Java, especialmente en el contexto de una API REST utilizando Spring Boot.

### 1.¿Qué es Optional en Java? ¿Por qué se recomienda su uso?

`Optional` es una clase contenedor introducida en Java 8 que puede contener un valor no nulo o estar vacío. Se utiliza para evitar errores de tipo `NullPointerException` al representar de forma explícita la posible ausencia de un valor.  
Se recomienda su uso porque mejora la legibilidad del código, fomenta el manejo explícito de valores opcionales y reduce errores relacionados con nulos.

---

### 2.¿Cuál es la diferencia entre `Optional.empty()`, `Optional.of()` y `Optional.ofNullable()`?

- `Optional.empty()` crea un `Optional` sin valor (vacío).  
- `Optional.of(value)` crea un `Optional` que contiene un valor no nulo; si el valor es nulo lanza `NullPointerException`.  
- `Optional.ofNullable(value)` crea un `Optional` que puede contener un valor o estar vacío, dependiendo de si el valor es nulo o no.

---

### 3.¿Qué ventajas tiene Optional frente a regresar null?

- Obliga al desarrollador a considerar la ausencia de valor desde el diseño del código.  
- Reduce la posibilidad de errores en tiempo de ejecución por `NullPointerException`.  
- Mejora la legibilidad y autodescripción del código.  
- Permite el uso de métodos funcionales como `.map()`, `.filter()`, `.ifPresent()` y `.orElse()` para manejar el valor de forma segura.

---

### 4.¿Cómo se integra Optional en Spring Data JPA?

Spring Data JPA permite usar `Optional` como tipo de retorno en los métodos del repositorio. Por ejemplo:

```java
Optional<User> findByUsername(String username);
```

Esto indica que el resultado puede estar presente o no, y obliga a manejar ambas posibilidades.  
Al integrarse con `Optional`, se mejora la seguridad ante nulos y se promueve un manejo más claro de resultados opcionales en las consultas a la base de datos.

---

### 5.¿Qué método de Optional permite lanzar una excepción si no hay resultado?

El método `orElseThrow()` de la clase `Optional` permite lanzar una excepción si no hay valor presente.  
Devuelve el valor si está contenido, y en caso contrario lanza una excepción, que puede ser personalizada mediante una lambda.  
Esto permite controlar de manera segura la ausencia de valores sin necesidad de verificar explícitamente si el objeto es `null`.

### 6.¿Por qué es útil Optional en el contexto de una API REST?

El uso de `Optional` en una API REST es útil porque:

- Permite manejar explícitamente la posible ausencia de valores.
- Evita errores como `NullPointerException`.
- Facilita generar respuestas HTTP apropiadas como `404 Not Found` o `200 OK`.
- Mejora la mantenibilidad del código y fomenta el uso de patrones funcionales.
- Desacopla la lógica de negocio de la lógica de presentación.

#### Ejemplo en Spring Boot:

```java
@GetMapping("/usuarios/{id}")
public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
    Optional<Usuario> usuario = usuarioRepository.findById(id);

    return usuario.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
}
```

Este código busca un usuario en la base de datos y devuelve una respuesta HTTP 200 OK si lo encuentra, o 404 Not Found si no existe.

## Parte 2: Implementación Técnica

Se proponen los siguientes ejercicios de implementación utilizando `Optional` en un proyecto con Spring Boot:

1. **Aplicar Optional en operaciones por búsqueda de parámetros**  
   - Por ejemplo, búsquedas por nombre, correo electrónico o identificación.  
   - Ejemplo: `Optional<Estudiante> findByNombre(String nombre);`

2. **Crear una operación para obtener estudiantes por materia**  
   - Implementar un endpoint que devuelva una lista de estudiantes inscritos en una materia específica.
   - Ejemplo: `Optional<List<Estudiante>> findByMateria(String materia);`
