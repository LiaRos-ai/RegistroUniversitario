# ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
Optional es una herramienta poderosa en Java que ayuda a escribir código más seguro, legible y robusto al abordar de manera explícita la posibilidad de valores ausentes, fomentando así mejores prácticas de programación y reduciendo la probabilidad de NullPointerException.
La adopción de Optional se recomienda encarecidamente por varias razones fundamentales:
Eliminación de NullPointerException (o al menos, su reducción drástica)
Mejor legibilidad y comprensión del código
Fomenta un diseño de API más robusto
Facilita el uso de la programación funcional
Evita comprobaciones de null anidadas

# ¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?

## Diferencia entre `Optional.empty()`, `Optional.of()` y `Optional.ofNullable()`?

`Optional` es una clase en Java que se usa para representar valores que pueden estar presentes o ausentes, evitando así errores como el `NullPointerException`.

##  Diferencias entre sus métodos principales son:

### `Optional.empty()`
- Crea un `Optional` vacío, es decir, sin ningún valor dentro.
- Se usa cuando queremos indicar que no hay valor disponible.

### `Optional.of(valor)`
- Crea un `Optional` con un valor **no nulo**.
- Si se pasa un valor nulo, lanza una excepción (`NullPointerException`).
- Se usa cuando estamos seguros de que el valor **no es nulo**.

### `Optional.ofNullable(valor)`
- Crea un `Optional` que puede contener un valor o estar vacío si el valor pasado es `null`.
- Se usa cuando **no estamos seguros** de si el valor puede ser nulo o no.
# ¿Qué ventajas tiene Optional frente a regresar null?
Optional ofrece varias ventajas frente a simplemente regresar null, especialmente en lenguajes como Java. Optional permite una gestión más clara y segura de valores que podrían ser nulos, evitando NullPointerException y mejorando la legibilidad del código.

*   **Evita NullPointerException:** Al usar Optional, puedes evitar tener que verificar explícitamente si un valor es null antes de acceder a él, lo que reduce la posibilidad de cometer errores de ejecución. 
    
*   **Mejora la legibilidad:** El uso de Optional hace que el código sea más claro y fácil de entender, ya que indica explícitamente que un valor puede estar presente o no, según el sitio de Reddit. 
    
*   **Facilita el manejo de valores ausentes:** Optional ofrece métodos como orElse, orElseGet, y orElseThrow que permiten manejar la ausencia de un valor de manera elegante y flexible. 
    
*   **Promueve la programación funcional:** Optional se adapta bien a los principios de la programación funcional, ya que permite cadenas de operaciones y manipulación de datos de manera más segura y eficiente.
    

**Ejemplo:**

String nombre = "Juan";

// Con Optional:

Optional nombreOptional = Optional.ofNullable(nombre);

nombreOptional.ifPresent(nom -> System.out.println("Nombre: " + nom));  // Imprimirá "Nombre: Juan"

// Sin Optional (podría dar NullPointerException):

if (nombre != null) {

    System.out.println("Nombre: " + nombre);

}

En resumen, Optional proporciona una forma más segura, clara y eficiente de manejar valores que pueden ser null, lo que contribuye a mejorar la calidad del código y reducir el riesgo de errores.
# ¿Cómo se integra Optional en Spring Data JPA?
## Agregar las dependencias necesarias
El archivo pom.xml debe incluir la dependencia:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!-- Driver JDBC según tu base de datos -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
## Crear una entidad JPA
Se debe crear (si es que no se tiene) una Entidad JPA
´´´
import jakarta.persistence.*;
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    // Getters y Setters
}
´´´

## Crear el repositorio con Optional
´´´
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository    
<Usuario, Long> {
    // Retorna un Optional
    Optional<Usuario> findByEmail(String email);
}

´´´
## Usar Optional en el servicio o controlador
´´´
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public void buscarPorEmail(String email) {
        Optional <Usuario> usuario = usuarioRepository. 
findByEmail(email);
        if (usuario.isPresent()) {
            System.out.println("Usuario encontrado: " + 
usuario.get().getNombre());
        } else {
            System.out.println("Usuario no encontrado");
        }
        usuario.ifPresent(u -> System.out.println("Hola, " +
 u.getNombre()));
    }
}
´´´

## Manejo más avanzado con Optional

String mensaje = usuarioRepository.findByEmail(email)
    .map(u -> "Usuario encontrado: " + u.getNombre())
    .orElse("Usuario no encontrado");
System.out.println(mensaje);
# ¿Qué método de Optional permite lanzar una excepción si no hay resultado?

El método `orElseThrow()` de la clase `Optional` en Java se utiliza para lanzar una excepción cuando no hay un valor presente. Es útil cuando la ausencia de un valor debe manejarse como un error.

## Variantes disponibles

### 1. `orElseThrow()` (Java 10+)
- **Descripción**: Lanza una `NoSuchElementException` si el `Optional` está vacío.
- **Ejemplo**:
  ```
  Optional<String> optional = Optional.empty();
  String valor = optional.orElseThrow(); // Lanza NoSuchElementException
  ```

### 2. `orElseThrow(Supplier<? extends X> exceptionSupplier)`
- **Descripción**: Permite lanzar una excepción personalizada si el `Optional` no contiene un valor.
- **Ejemplo**:
  ```
  Optional<String> optional = Optional.empty();
  String valor = optional.orElseThrow(() -> new RuntimeException("Valor no encontrado"));
  ```

## Uso Recomendado
```java
Optional<String> resultado = obtenerValorOpcional();
String valor = resultado.orElseThrow(() -> new IllegalStateException("El valor es requerido"));
```

## ¿Cuándo usarlo?
- Cuando la ausencia de un valor **debe** considerarse un error.
- En lugar de `get()` (que también lanza `NoSuchElementException` pero sin claridad semántica).
- Para personalizar la excepción lanzada con un mensaje específico.

# ¿Por qué es útil `Optional` en el contexto de una API REST?
`Optional` es útil para manejar la posibilidad de que una respuesta sea vacía o no devuelva ningún dato, sin necesidad de lanzar errores. Proporciona un contenedor que puede o no contener un valor no nulo, lo cual ayuda a escribir código más limpio, seguro y expresivo, especialmente en operaciones como búsquedas de recursos por ID o parámetros opcionales en una petición.
