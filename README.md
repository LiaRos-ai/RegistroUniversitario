# Parte 1: Investigación

## ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?

Optional es una clase en Java que representa un valor que puede o no estar presente, se recomienda su uso para evitar los siguientes problemas: Null pointer exceptions, falta de claridad en el código y Problemas de concurrencia.

## ¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?

* Optional.empty(): Crea un objeto Optional vacío, es decir, uno que no contiene un valor.
* Optional.of(): Crea un objeto Optional con el valor especificado, si el valor es null, se produce una excepción NullPointerException.
* Optional.ofNullable(): Crea un objeto Optional con el valor especificado. si el valor es null, devuelve un objeto Optional vacío.

## ¿Qué ventajas tiene Optional frente a regresar null?

* **Evita errores comunes** como **NullPointerException.**
* **Mejora la legibilidad** del código, ya que indica claramente que un valor puede estar presente o no.
* **Fomenta un manejo más seguro** y estructurado del valor devuelto, evitando validaciones manuales con **```if (obj != null)```**.

## ¿Cómo se integra Optional en Spring Data JPA?

Spring Data JPA permite que tus métodos de repositorio devuelvan Optional&lt;T> para indicar que el resultado puede estar o no.

```Java
//Ejemplo
Optional<Usuario> usuario = usuarioRepository.findById(123L);
```

Luego puedes trabajar con ese Optional así:

```Java
usuario.ifPresent(u -> System.out.println(u.getNombre()));

Usuario u = usuario.orElseThrow(() -> new RuntimeException("No encontrado"));
```

## ¿Qué método de Optional permite lanzar una excepción si no hay resultado?

El método **```orElseThrow()```  **lanza una** **excepción** ```NoSuchElementException```** si no hay valor, si queremos personalizar la excepción que se lanzará usamos **```orElseThrow(Supplier<Throwable> exceptionSupplier)```**

## ¿Por qué es útil Optional en el contexto de una API REST?

Optional es útil para manejar la ausencia de un valor de manera explícita y segura, por ejemplo, en lugar de devolver null cuando no se encuentra un recurso, puedes devolver un objeto Optional vacío.
