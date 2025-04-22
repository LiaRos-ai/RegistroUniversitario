# Uso de Optional en Java (Activida #4)
## Integrantes:
- Aaron Oswaldo Nina Calzada
- Genesis Jalid Tapia Cortez
- Carlos Manuel Miranda Aguirre

Nota: Los tres integrantes colaboraron en todo el desarrollo del proyecto, incluyendo Parte 1: Investigación y Parte 2: Implementación Técnica de optional

## ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
Optional<T> es una clase contenedora que puede almacenar un valor del tipo T o estar vacío. Se utiliza para representar valores que pueden o no estar presentes, evitando el uso explícito de null. Se recomienda su uso porque:
- Reduce errores como el NullPointerException.
- Hace el código más legible y seguro.
- Obliga al programador a considerar la ausencia de valores.

## ¿Cuál es la diferencia entre Optional.empty(), Optional.of() y Optional.ofNullable()?
- Optional.empty(): Crea un Optional sin valor, es decir, vacío.
- Optional.of(value): Crea un Optional con un valor *no nulo*. Si el valor es null, lanza NullPointerException.
- Optional.ofNullable(value): Crea un Optional que puede contener un valor o estar vacío si el valor es null.

## ¿Qué ventajas tiene Optional frente a regresar null?
- Elimina la necesidad de verificaciones explícitas de null.
- Hace evidente en la firma del método que un valor puede estar ausente.
- Facilita un estilo funcional de programación con métodos como map(), filter(), orElse(), etc.
- Mejora la mantenibilidad y reduce errores de tiempo de ejecución.

## ¿Cómo se integra Optional en Spring Data JPA?
Spring Data JPA permite retornar Optional en los métodos de repositorio. Por ejemplo:
java
Optional<User> findById(Long id);

Esto indica claramente que el resultado puede no existir, evitando tener que manejar manualmente valores null.

## ¿Qué método de Optional permite lanzar una excepción si no hay resultado?
orElseThrow()

Este método lanza una excepción si el Optional está vacío. Puede usarse así:
java
User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));


## ¿Por qué es útil Optional en el contexto de una API REST?
En una API REST, muchas veces se consultan recursos que pueden no existir. Optional ayuda a manejar estos casos limpiamente, por ejemplo, retornando un 404 Not Found si un recurso no está presente. Además:
- Mejora la claridad del código.
- Permite escribir controladores más concisos y seguros.