# 🎯 Investigación sobre `Optional` en Java Grupo 11
> **"Un código seguro empieza por eliminar el miedo al NullPointerException."** ✨

# 🙍‍♂️ Integrantes

- Alexander Nataniel Castillo Centellas
- Jhoel Alexander Chipana Paye
- Adriana Valeria Fernadez Flores
- Jhamil Elias Mamani Colque
- Edson Javier Mamani Ticona
- Amiel Natanieli Mendez Vargas
- Israel Andres Quenta Pomacusi
- Adrian Marcelo Requena Oros
- Maritza Zarate Paco

---

# 📚 ¿Qué es `Optional`?

`Optional` es una **clase genérica** introducida en **Java 8** en el paquete `java.util`.  
Su objetivo principal es **representar un valor que puede estar presente o ausente**, evitando directamente el uso de valores nulos (`null`).

**¿Por qué se recomienda su uso?**
- 🚫 Ayuda a evitar errores por `NullPointerException`.
- 📖 Mejora la **legibilidad** y **expresividad** del código.
- ⚙️ Promueve buenas prácticas de programación funcional.
- 🤝 Facilita la integración con frameworks como **Spring Data**.
- 🔒 Obliga al desarrollador a manejar de forma segura los valores que pueden no existir.

---

# 🌟 Diferencias entre `Optional.empty()`, `Optional.of()` y `Optional.ofNullable()`

| Método | Descripción |
|--------|-------------|
| `Optional.empty()` | Devuelve una instancia vacía de `Optional`, es decir, **sin valor**. |
| `Optional.of(T value)` | Crea un `Optional` con un **valor no nulo**. Si se pasa un valor `null`, lanza `NullPointerException`. |
| `Optional.ofNullable(T value)` | Crea un `Optional` que puede contener un valor o estar vacío si el valor es `null`. |

---

# 🚀 Ventajas de `Optional` frente a regresar `null`

- 🔥 **Evita `NullPointerException`**: Minimiza errores por referencias nulas.
- 🧹 **Código más limpio y legible**: Hace explícito que un valor puede estar ausente.
- 🔄 **Mejor manejo del flujo de datos**: Métodos como `map()`, `filter()`, `flatMap()` e `ifPresent()` permiten operar sobre los valores de manera fluida y segura.
- 📜 **Enfoque más declarativo**: Obliga a considerar todos los casos posibles, aumentando la calidad y robustez del código.

---

# 🛠️ Integración de `Optional` en **Spring Data JPA**

En **Spring Data JPA**, muchos métodos de repositorios, como `findById()`, retornan un `Optional`, obligando al manejo explícito de resultados ausentes.

**Ejemplo:**

```java
Optional<Estudiante> estudiante = estudianteRepository.findById(id);

estudiante.ifPresent(e -> System.out.println(e.getNumeroInscripcion()));
```
# ⚡ Método de `Optional` para lanzar una excepción si no hay resultado

El método `orElseThrow()` permite lanzar una excepción si el `Optional` está vacío.

**Ejemplo aplicado:**

```java
Estudiante estudiante = estudianteRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
```
# 🌐 Utilidad de Optional en una API REST
- 📭 Manejo explícito de respuestas vacías: Permite retornar códigos HTTP como 404 Not Found si un recurso no existe.
- 🛡️ Evita errores en controladores: Facilita el manejo seguro de atributos opcionales como usuarioAlta o fechaAlta en las respuestas de un endpoint REST.
- 🔄 Flujo más predecible: Ayuda a construir APIs más limpias y robustas, donde el cliente sabe si el recurso fue encontrado o no, de manera clara.
# 📢 Conclusión
El uso de Optional en Java no solo mejora la calidad del código, sino que también ayuda a crear aplicaciones más seguras, limpias y fáciles de mantener.
Especialmente en proyectos modernos con frameworks como Spring, adoptar Optional se considera una buena práctica que favorece la estabilidad, claridad y consistencia de las aplicaciones.
