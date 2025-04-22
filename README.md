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