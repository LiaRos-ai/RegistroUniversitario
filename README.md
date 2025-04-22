# Investigación: Uso de Optional en Java

## ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
`Optional` es una clase contenedor introducida en Java 8 que representa un valor que puede estar presente o no (es decir, opcional). Se recomienda su uso para evitar errores comunes como el `NullPointerException`, y para hacer explícito que un valor puede ser nulo, mejorando así la legibilidad y robustez del código.

## ¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?
- `Optional.empty()`: Devuelve un `Optional` vacío (sin valor).
- `Optional.of(value)`: Crea un `Optional` con un valor no nulo. Lanza `NullPointerException` si el valor es `null`.
- `Optional.ofNullable(value)`: Crea un `Optional` que puede contener un valor nulo o no. Si el valor es `null`, devuelve un `Optional.empty()`.

## ¿Qué ventajas tiene Optional frente a regresar null?
- Elimina el riesgo de `NullPointerException`.
- Obliga al programador a manejar explícitamente los valores ausentes.
- Mejora la legibilidad del código al indicar claramente que un valor puede no estar presente.
- Facilita operaciones funcionales como `map()`, `filter()` y `ifPresent()`.

## ¿Cómo se integra Optional en Spring Data JPA?
Spring Data JPA permite usar `Optional` como tipo de retorno en los métodos de repositorios. Por ejemplo:
```java
Optional<Usuario> findByEmail(String email);
```
Esto permite trabajar de manera más segura con posibles valores nulos que vienen de la base de datos, evitando excepciones innecesarias.

## ¿Qué método de Optional permite lanzar una excepción si no hay resultado?
El método `orElseThrow()` lanza una excepción si el valor no está presente. Se puede usar con una lambda para lanzar una excepción personalizada:
```java
usuarioOptional.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
```

## ¿Por qué es útil Optional en el contexto de una API REST?
En una API REST, `Optional` permite manejar adecuadamente los recursos no encontrados (por ejemplo, con `404 Not Found`) en vez de retornar `null`, lo que mejora la claridad del código y facilita el control de errores y las respuestas HTTP apropiadas.

---

**Integrantes del grupo:**
- Brandon Jhosef Amezaga Garrido
- Maria Teresa Aspiazu Sanches
- Jesus Abed Herrera Sirpa
- Joel Alejandro Perez Murillo
- Ariadne Checcid Quiroz Coila
- Cael Mathew Cuevas Alconini
- Jose Alfredo Choque Choque