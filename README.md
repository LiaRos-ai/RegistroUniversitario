# Optional en Java - Guía Rápida  

## 📌 ¿Qué es Optional?  
Clase contenedora (`java.util.Optional`) introducida en Java 8 para representar un valor que puede ser `null`. Su objetivo es evitar `NullPointerException` y manejar casos nulos de forma explícita.  

## 🎯 ¿Por qué usarlo?  
- **Expresa claridad**: Indica que un valor puede estar ausente.  
- **Evita `null`**: Reduce errores como `NullPointerException`.  
- **API fluida**: Permite operaciones encadenadas (`map`, `filter`, etc.).  

## 🛠️¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?  
| Método                | Descripción                                                                 | Ejemplo                                  |  
|-----------------------|-----------------------------------------------------------------------------|------------------------------------------|  
| `Optional.empty()`    | Retorna un `Optional` vacío.                                               | `Optional.empty()`                       |  
| `Optional.of(valor)`  | Crea un `Optional` con un valor **no nulo** (error si es `null`).          | `Optional.of("Hola")`                    |  
| `Optional.ofNullable(valor)` | Crea un `Optional` que acepta `null`.                              | `Optional.ofNullable(null)`              |  
| `orElse(default)`     | Retorna el valor o un predeterminado.                                      | `opt.orElse("default")`                  |  
| `orElseThrow()`       | Lanza una excepción si no hay valor.                                       | `opt.orElseThrow(() -> new Exception())` |  
| `isPresent()`         | Verifica si hay valor.                                                     | `if (opt.isPresent()) { ... }`           |  

## ✅ ¿Qué ventajas tiene Optional frente a regresar `null`? 
- **Código más seguro**: Evita checks `if (obj != null)`.  
- **Legibilidad**: Métodos como `orElse()` hacen el código más declarativo.  

## 🔄 ¿Cómo se integra Optional en Spring Data JPA?	  
Los repositorios pueden retornar `Optional` en consultas:  
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```
## 🔄 ¿Qué método de Optional permite lanzar una excepción si no hay resultado?	  
El método `orElseThrow()` de `Optional` permite lanzar una excepción si el valor no está presente. Por ejemplo:
```java
Usuario usuario = usuarioRepository.findById(id)
                      .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
```
## 🔄¿Por qué es útil Optional en el contexto de una API REST?
En una API REST, `Optional` permite manejar respuestas en las que un recurso puede no estar presente sin depender de `null`. Se puede utilizar en servicios para evitar respuestas inesperadas y mejorar la robustez de la API.
