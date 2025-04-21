# ACTIVIDAD – Optional en Java

## ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
R. En lugar de devolver un valor que puede ser nulo, se usa la clase Optional en Java para indicar que el valor puede o no estar presente. Esto ayuda a que los desarrolladores puedan manejar nulos de manera explícita.
Optional es una clase de Java que representa un contenedor que puede o no contener un valor (es decir, es opcional). Se recomienda para evitar null, el cual puede provocar excepciones como NullPointerException. 
Optional es una clase contenedora introducida en Java para manejar valores que pueden estar presentes o ausentes, evitando así errores como NullPointerException. Se recomienda su uso porque mejora la legibilidad del código, promueve buenas prácticas y fomenta la programación funcional.

## ¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?
R. Son las siguientes:
 * Optional.empty(): Crea un Optional vacío (sin valor).
 * Optional.of(valor): Crea un Optional con un valor no nulo. Lanza excepción si el valor es null.
 * Optional.ofNullable(valor): Acepta un valor que puede ser nulo; si es null, retorna un Optional vacío.

## ¿Qué ventajas tiene Optional frente a regresar null?
R. Tiene como ventajas las siguientes:
* Evita errores por NullPointerException
* Hace explícito que el valor puede estar ausente.
* Permite trabajar de forma funcional con métodos como map, filter, orElse, etc.
* Mejora la legibilidad y mantenibilidad del código.

## ¿Cómo se integra Optional en Spring Data JPA?
R. Spring Data JPA utiliza Optional para gestionar resultados de consultas que pueden estar vacíos, como en findById(), asegurando un manejo limpio de los valores no encontrados, por **Ejemplo**:
```
 Optional<Estudiante> findById(Long id);
```
## ¿Qué método de Optional permite lanzar una excepción si no hay resultado?
R. El método ``` orElseThrow() ``` permite lanzar una excepción si el valor no está presente en el Optional, ejemplo:
```
Estudiante e = estudianteRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
```
## ¿Por qué es útil Optional en el contexto de una API REST?
R. Es útil porque permite manejar respuestas vacías sin lanzar errores innecesarios. Ayuda a retornar respuestas adecuadas (como 404 Not Found) cuando un recurso no existe, y mejora el manejo de flujos condicionales en las peticiones.
