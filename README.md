grupo7-springb
Grupo 7
NOMBRES: 
Lenz Abad Alanoca Ojeda
Juan Vidal Mamani Riveros
Herlan Choque Flores
Lorgio Emilio Chura Carrillo
Jesus Alejandro Cruz
Juan Carlos Limachi Maydana



1. ¿Qué es `Optional` en Java? ¿Por qué se recomienda su uso?  
`Optional` es una clase introducida en Java 8 dentro del paquete `java.util`, que actúa como un contenedor para valores que pueden o no estar presentes. Su principal propósito es reducir el uso de valores `null`, evitando errores comunes como `NullPointerException` y mejorando la legibilidad y seguridad del código. Se recomienda su uso porque ayuda a que el código sea más explícito sobre la posibilidad de valores ausentes, facilitando el manejo de excepciones y la implementación de flujos funcionales más concisos y seguros.

Ejemplo :
Optional<String> nombre = Optional.of("Juan");
System.out.println(nombre.orElse("No disponible"));

2. ¿Cuál es la diferencia entre `Optional.empty()`, `Optional.of()`, y `Optional.ofNullable()`?  
Cada uno de estos métodos tiene propósitos específicos:
- `Optional.empty()`: Devuelve un `Optional` sin ningún valor, útil para indicar explícitamente que no hay resultado.  
 
  Optional<String> opcionalVacio = Optional.empty();
- `Optional.of(T value)`: Crea un `Optional` con un valor no nulo; si el valor es `null`, lanza una excepción.  
  Optional<String> opcionalConValor = Optional.of("Hola");
- `Optional.ofNullable(T value)`: Crea un `Optional` que puede contener `null`, evitando excepciones y facilitando la integración con datos inciertos.  
  Optional<String> opcionalSeguro = Optional.ofNullable(null);
  
3. ¿Qué ventajas tiene `Optional` frente a regresar `null`?  
`Optional` ofrece varios beneficios en comparación con `null`:
- Evita `NullPointerException`, mejorando la estabilidad del código.  
- Explicita la ausencia de valores, facilitando la lectura del código.  
- Permite flujos funcionales con métodos como `map()`, `filter()`, `ifPresent()` y `orElseThrow()`.  
- Mejora la estructura del código, eliminando la necesidad de verificaciones manuales de `null`.

Ejemplo de mejora:
Optional<String> mensaje = Optional.ofNullable(obtenerMensaje());
mensaje.ifPresent(System.out::println);
Aquí no es necesario hacer `if (mensaje != null)`, lo que simplifica la lógica.
4. ¿Cómo se integra `Optional` en Spring Data JPA?  
Spring Data JPA permite el uso de `Optional` en repositorios al definir métodos de acceso a datos. Esto hace que el código sea más seguro y evita verificaciones manuales de `null`.
Ejemplo de un método en un repositorio JPA:
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}

Luego, en el servicio podemos manejar el resultado de manera segura:
public Usuario obtenerUsuarioPorEmail(String email) {
    return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
}
5. ¿Qué método de `Optional` permite lanzar una excepción si no hay resultado?  
El método `orElseThrow()` lanza una excepción si el valor está ausente:
Optional<Usuario> usuario = usuarioRepository.findById(id);
Usuario u = usuario.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
Esto es útil en APIs REST donde se espera una respuesta clara en caso de datos inexistentes.
6. ¿Por qué es útil `Optional` en el contexto de una API REST?  
En APIs REST, `Optional` ofrece varias ventajas:
- Permite manejar respuestas HTTP adecuadamente, por ejemplo, devolviendo `404 Not Found` en caso de datos inexistentes.  
- Reduce el uso de valores `null`, facilitando la gestión de errores en controladores.  
- Mejora la estructura del código y evita verificaciones innecesarias.

Ejemplo en un controlador:
java
@GetMapping("/usuarios/{id}")
public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
    return usuarioRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
}
=======
# 📋 ACTIVIDAD 4 - Optional en Java

## 🔍 Parte 1: Investigación

**1. ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?**  
R1. Optional es una clase contenedora introducida en Java 8 (java.util.Optional) que representa un valor que puede o no estar presente (puede ser nulo o no nulo).  
- Expresa intención claramente: Indica explícitamente que un método podría no tener un valor para devolver.  
- Reduce NullPointerExceptions: Obliga a manejar conscientemente el caso de ausencia de valor.  
- Proporciona una API más limpia.  

**2. ¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?**  
R2.  
- Optional.empty(): Retorna un Optional vacío (sin valor).  
- Optional.of(valor): Crea un Optional con un valor no nulo. Si el valor es null, lanza NullPointerException.  
- Optional.ofNullable(valor): Crea un Optional que acepta valores nulos. Si el valor es null, retorna un Optional vacío.  

**3. ¿Qué ventajas tiene Optional frente a regresar null?**  
R3. Las ventajas son las siguientes:  
- Optional indica explícitamente que un valor puede estar ausente.  
- Devolver null no comunica explícitamente que un método puede no tener un resultado válido.  
- Con Optional, se obliga al desarrollador a manejar explícitamente el caso en el que no hay valor.  
- Con null, si no se verifica, puede provocar una NullPointerException.  
- Optional permite usar métodos como .map(), .filter(), .flatMap(), etc.  
- Optional está diseñado para integrarse bien con la API de Streams de Java.  
- El tipo de retorno Optional<T> documenta automáticamente que el método puede no devolver un valor.  
- En Spring Data JPA, los métodos de repositorio pueden retornar Optional para manejar casos donde no se encuentra una entidad.  

**4. ¿Cómo se integra Optional en Spring Data JPA?**  
R4. En Spring Data JPA, Optional se utiliza para manejar de manera segura los casos en los que una consulta no retorna resultados, evitando errores como el NullPointerException. Métodos como findById() devuelven un Optional<T>, lo que permite verificar si un valor está presente utilizando métodos como isPresent(), ifPresent(), o orElse().  

**5. ¿Qué método de Optional permite lanzar una excepción si no hay resultado?**  
R5. orElseThrow(): Lanza una excepción si el Optional está vacío.  

**6. ¿Por qué es útil Optional en el contexto de una API REST?**  
R6. Optional es útil en una API REST porque permite manejar de forma clara, segura y elegante la ausencia de datos.  
- Expresa de forma clara la posible ausencia de datos.  
- Evita el uso de null y reduce errores como NullPointerException.  
- Facilita el manejo de respuestas como 404 Not Found.  
- Mejora la legibilidad y documentación del código.  
- Permite un flujo de manejo funcional y seguro de los datos.  

