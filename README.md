Preguntas sobre Optional en Java
¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
Optional es una clase contenedora introducida en Java 8 que representa un valor que puede estar presente o ausente. Es decir, es una forma de manejar el concepto de valores opcionalmente nulos de una manera más segura y expresiva, evitando errores comunes como el famoso NullPointerException.

Se recomienda su uso porque permite:
- Hacer el código más legible y seguro, dejando explícito cuando un valor podría no existir.
- Fomentar una mejor gestión de la ausencia de datos sin recurrir a retornos nulos.
- Permite usar operaciones funcionales como map(), filter(), ifPresent() o orElse() para trabajar con el dato si está presente.

En entornos de programación web (por ejemplo, con Spring o en controladores REST), usar Optional puede mejorar la claridad de las respuestas, sobre todo al trabajar con datos que pueden o no existir (como una búsqueda por ID en la base de datos).
¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?
Estas son tres formas de construir instancias de Optional, y cada una tiene su propósito:

- Optional.empty():
Crea un Optional vacío, es decir, sin valor. Es el equivalente a tener un dato "ausente" o nulo, pero de forma segura.
```java
Optional<String> opt = Optional.empty();

- Optional.of(value):
Crea un Optional que contiene un valor no nulo. Si se pasa un valor nulo, lanza una NullPointerException. Se usa cuando estás seguro de que el valor no es nulo.
```java
Optional<String> opt = Optional.of("Hola");

- Optional.ofNullable(value):
Crea un Optional que puede contener un valor o estar vacío si el valor es nulo. Es la forma más segura cuando no estás seguro si el valor puede ser nulo.
```java
Optional<String> opt = Optional.ofNullable(posibleNulo);
¿Qué ventajas tiene Optional frente a regresar null?
Usar Optional tiene varias ventajas importantes frente a retornar null:

- Evita errores por nulos: Te obliga a manejar el caso en que el valor no esté presente, reduciendo el riesgo de NullPointerException.
- Hace el contrato del método más claro: Al ver que un método devuelve Optional<T>, queda explícito que puede no haber resultado. Con null, esto no es obvio.
- Permite construir flujos funcionales: Gracias a métodos como map(), flatMap(), filter(), puedes encadenar operaciones de forma elegante y segura.
- Facilita testing y mantenimiento: Porque reduce las condiciones inesperadas y mejora la previsibilidad del código.

En resumen, es una forma moderna y más segura de tratar la ausencia de datos.
¿Cómo se integra Optional en Spring Data JPA?
Spring Data JPA se integra de manera natural con Optional. Por ejemplo, los repositorios pueden retornar Optional<T> al buscar por ID u otras propiedades:

Optional<Producto> producto = productoRepository.findById(5L);

Esto obliga al desarrollador a manejar correctamente el caso en que el resultado no exista:

producto.ifPresent(p -> {
// hacer algo con el producto
});

O manejar el caso contrario:

Producto producto = productoRepository.findById(id)
.orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

Esto es especialmente útil al trabajar con APIs REST, ya que puedes devolver respuestas HTTP apropiadas (como 404) cuando no se encuentra un recurso.
¿Qué método de Optional permite lanzar una excepción si no hay resultado?
El método es orElseThrow().

Este método permite lanzar una excepción personalizada si el valor no está presente en el Optional. Es muy útil para manejar errores de forma controlada en APIs, servicios o lógica de negocio.

Ejemplo:
Producto producto = productoRepository.findById(id)
.orElseThrow(() -> new RecursoNoEncontradoException("Producto con ID " + id + " no encontrado"));

Este patrón es común en aplicaciones web donde se necesita manejar errores HTTP como 404 Not Found de forma precisa.
¿Por qué es útil Optional en el contexto de una API REST?
En el desarrollo de APIs REST, muchas veces necesitas devolver recursos que podrían no existir, como por ejemplo al hacer una consulta por ID. En estos casos:

- Usar Optional permite distinguir claramente entre un recurso encontrado o no.
- Facilita el mapeo automático de errores como 404 Not Found o 204 No Content.
- Mejora la claridad del código del controlador, porque te obliga a manejar los casos ausentes.

Ejemplo en un controlador REST con Spring Boot:
@GetMapping("/productos/{id}")
public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
return productoRepository.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

Este código es elegante, seguro y responde con los estados HTTP correctos sin complicaciones adicionales.