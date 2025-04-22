# ACTIVIDAD 4

## 1. ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
**Optional** es una clase contenedora que se utiliza para representar un valor que puede estar presente o no. Fue introducido en Java 8 con el objetivo de evitar el uso de `null` y prevenir los errores `NullPointerException`. Se recomienda su uso porque proporciona una forma más explícita y segura de manejar valores ausentes, evitando las comprobaciones manuales de `null`.

## 2. ¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?
- **Optional.empty()**: Devuelve un Optional vacío, es decir, un contenedor que no contiene ningún valor.
- **Optional.of()**: Crea un Optional con un valor no nulo. Si se pasa `null`, lanzará una excepción `NullPointerException`.
- **Optional.ofNullable()**: Crea un Optional que puede contener un valor no nulo o ser vacío. Si el valor es `null`, devolverá un Optional vacío.

## 3. ¿Qué ventajas tiene Optional frente a regresar null?
- **Seguridad**: Optional fuerza al programador a manejar explícitamente la ausencia de valor, lo que reduce el riesgo de errores por acceso a `null`.
- **Claridad**: El uso de Optional hace explícito que el valor puede estar ausente, mejorando la legibilidad del código.
- **Métodos útiles**: Optional ofrece métodos como `ifPresent()`, `orElse()`, y `orElseThrow()` para manejar el valor de manera fluida y funcional.

## 4. ¿Cómo se integra Optional en Spring Data JPA?
En Spring Data JPA, **Optional** se utiliza comúnmente en los métodos de los repositorios, como los que devuelven un único resultado (por ejemplo, `findById()`). Al utilizar Optional, se garantiza que el valor de retorno puede estar presente o ausente sin tener que comprobar manualmente si es `null`. Esto permite un manejo más seguro de los resultados de las consultas.

## 5. ¿Qué método de Optional permite lanzar una excepción si no hay resultado?
El método `orElseThrow()` de Optional permite lanzar una excepción si el valor dentro del Optional está ausente.
Optional<String> result = Optional.empty();
String value = result.orElseThrow(() -> new NoSuchElementException("No value present"));

## 6. ¿Por qué es útil Optional en el contexto de una API REST?
En una API REST, el uso de **Optional** es útil para manejar respuestas que pueden o no tener un valor, como cuando una búsqueda no devuelve ningún recurso. Al usar Optional, el código que consume la API puede tratar de forma más clara y controlada la ausencia de datos, evitando `null` y mejorando la semántica de las respuestas, como al devolver un `404 Not Found` o un `200 OK` con el valor ausente en el cuerpo de la respuesta.  

Esto mejora la claridad y ayuda a manejar adecuadamente las respuestas vacías, en lugar de usar `null` o códigos de error ambiguos.
