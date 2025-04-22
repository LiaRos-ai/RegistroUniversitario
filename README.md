# GRUPO 15
**Integrantes:** Julio Erick Picavia Saravia, Carlos Javier Callizaya Rosas

# Uso de `Optional` en Java

## ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?

`Optional` es una clase contenedora introducida en Java 8, diseñada para representar un valor que puede estar presente o ausente (es decir, evitar `null`).  
Se recomienda su uso porque hace **explícita** la posibilidad de ausencia de valor, ayudando a prevenir errores como `NullPointerException`.

---

## ¿Cuál es la diferencia entre `Optional.empty()`, `Optional.of()`, y `Optional.ofNullable()`?

- `Optional.empty()`  
  Crea un `Optional` sin valor (vacío).

- `Optional.of(valor)`  
  Crea un `Optional` con un valor no nulo. Si el valor es `null`, lanza `NullPointerException`.

- `Optional.ofNullable(valor)`  
  Crea un `Optional` que puede contener un valor o estar vacío, dependiendo de si el argumento es `null`.

---

## ¿Qué ventajas tiene Optional frente a regresar `null`?

- Evita errores por referencias nulas.
- Obliga a quien use el método a manejar explícitamente la ausencia de valor.
- Mejora la legibilidad y la intención del código.
- Ofrece métodos funcionales como `map`, `filter`, `orElse`, entre otros.

---

## ¿Cómo se integra Optional en Spring Data JPA?

Spring Data JPA permite usar `Optional` como tipo de retorno en repositorios. Por ejemplo:

```java
Optional<Usuario> findById(Long id);
```

Esto indica claramente que el resultado puede o no existir, y se integra naturalmente con el control de flujos de Spring (como respuestas HTTP 404).

---

## ¿Qué método de Optional permite lanzar una excepción si no hay resultado?

El método `orElseThrow()` lanza una excepción si el `Optional` está vacío.  
También puede usarse con una lambda personalizada:

```java
usuarioOptional.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
```

---

## ¿Por qué es útil Optional en el contexto de una API REST?

Porque ayuda a manejar de forma clara y segura los casos donde un recurso puede no existir.  
Por ejemplo, si no se encuentra un usuario por su ID, se puede devolver un **HTTP 404** en lugar de lanzar una excepción no controlada, mejorando la robustez y legibilidad de la API.
