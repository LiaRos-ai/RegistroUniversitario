ACTIVIDAD 4
¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
R. En lugar de devolver un valor que puede ser nulo, se usa la clase Optional en Java para indicar que el valor puede o no estar presente. Esto ayuda a que los desarrolladores puedan manejar nulos de manera explícita.
Optional es una clase de Java que representa un contenedor que puede o no contener un valor (es decir, es opcional). Se recomienda para evitar null, el cual puede provocar excepciones como NullPointerException.
¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?
R. Optional.empty(): Crea una instancia de Optional vacía (sin valor).
Optional.of(): Crea un Optional con un valor no nulo. Lanza una excepción si el valor es null.
Optional.ofNullable(): Crea un Optional que puede contener un valor o estar vacío si el valor es null.
¿Qué ventajas tiene Optional frente a regresar null?
R. Tiene como ventajas las siguientes:
Evita errores por NullPointerException
Hace explícito que el valor puede estar ausente.
Permite trabajar de forma funcional con métodos como map, filter, orElse, etc.
Mejora la legibilidad y mantenibilidad del código.
¿Cómo se integra Optional en Spring Data JPA?
R. Spring Data JPA utiliza Optional para gestionar resultados de consultas que pueden estar vacíos, como en findById(), asegurando un manejo limpio de los valores no encontrados, por ejemplo:
Optional<Estudiante> findById(Long id);
¿Qué método de Optional permite lanzar una excepción si no hay resultado?
R. El método orElseThrow() permite lanzar una excepción si el valor no está presente en el Optional, ejemplo:
Estudiante e = estudianteRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
¿Por qué es útil Optionalen el contexto de una API REST?
R. Permite manejar de forma elegante las respuestas vacías o inexistentes, facilitando el control de errores y permitiendo retornar respuestas claras como 404 Not Found cuando un recurso no se encuentra.
