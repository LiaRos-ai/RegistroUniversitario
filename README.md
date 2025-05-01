<<<<<<< HEAD
ACTIVIDAD 4
1.¿Qué es Optional en Java?¿Por qué se recomienda su uso?
Optional es una clase contenedora que se utiliza para representar un valor que puede estar presente o no. Fue introducido en Java 8 con el objetivo de evitar el uso de null y prevenir los errores NullPointerException. Se recomienda su uso porque proporciona una forma más explícita y segura de manejar valores ausentes, evitando las comprobaciones manuales de null.
2.¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()? 
Optional.empty(): Devuelve un Optional vacío, es decir, un contenedor que no contiene ningún valor.
Optional.of(): Crea un Optional con un valor no nulo. Si se pasa null, lanzará una excepción NullPointerException.
Optional.ofNullable(): Crea un Optional que puede contener un valor no nulo o ser vacío. Si el valor es null, devolverá un Optional vacío.
3.¿Qué ventajas tiene Optional frente a regresar null?
Seguridad: Optional fuerza al programador a manejar explícitamente la ausencia de valor, lo que reduce el riesgo de errores por acceso a null.
Claridad: El uso de Optional hace explícito que el valor puede estar ausente, mejorando la legibilidad del código.
Métodos útiles: Optional ofrece métodos como ifPresent(), orElse(), y orElseThrow() para manejar el valor de manera fluida y funcional
4.¿Cómo se integra Optional en Spring Data JPA?
En Spring Data JPA, Optional se utiliza comúnmente en los métodos de los repositorios, como los que devuelven un único resultado (por ejemplo, findById()). Al utilizar Optional, se garantiza que el valor de retorno puede estar presente o ausente sin tener que comprobar manualmente si es null. Esto permite un manejo más seguro de los resultados de las consultas.
5.¿Qué método de Optional permite lanzar una excepción si no hay resultado?
El método orElseThrow() de Optional permite lanzar una excepción si el valor dentro del Optional está ausente.
Optional<String> result = Optional.empty();
String value = result.orElseThrow(() -> new NoSuchElementException("No value present"));
6.¿Por qué es útil Optional en el contexto de una API REST?
En una API REST, el uso de Optional es útil para manejar respuestas que pueden o no tener un valor, como cuando una búsqueda no devuelve ningún recurso. Al usar Optional, el código que consume la API puede tratar de forma más clara y controlada la ausencia de datos, evitando null y mejorando la semántica de las respuestas, como al devolver un 404 Not Found o un 200 OK con el valor ausente en el cuerpo de la respuesta.
Esto mejora la claridad y ayuda a manejar adecuadamente las respuestas vacías, en lugar de usar null o códigos de error ambiguos.
@GetMapping("/users/{id}")
public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity.notFound().build());
}
=======
2025# Proyecto CRUD - Universidad

Este repositorio contiene la base del proyecto Universitario utilizando **Spring Boot**. Cada grupo debe trabajar en su propia rama según las instrucciones del docente.

---

## 🚀 Objetivo

Completar las operaciones requeridas sobre el Proyecto.

**Grupos y ramas asignadas**

|**Grupo**|**Ramaogiana**|**Integrantes**|
| :-: | :-: | :-: |
|01|grupo-01-springb|[Enrique Rafael Fernández Chiri, Nohemy Ruth Mamani Apaza, Samantha Rousse Gironda Mina, Josué Misael López Huanca, Luz Belén Chávez Patzi, Oscar Joel Choque Flores, Cristhian Pablo Álvarez Guarachi]|
|02|grupo-02-springb|[Maya Cádiz, Leandro Chávez, Masiel Chirinos, Sergio Luque, Jordy Miranda, Saúl Sánchez, Elizabeth Suzaño]|
|03|grupo-03-springb|[Bautista Mollo Denzel Guden, Copa Quispe Esther Sara, Guarachi Arguata Alberth, Reyes Barja Carlos Eduardo, Rojas Condoria Fidel Angel, Tancara Suñagua Joel Hernan.]|
|04|grupo-04-springb|[Marcelo Alejandro Villarroel Gutiérrez, Jonathan Gerson Gutiérrez Condori, Betzabe Gutiérrez Morales, Mikaela Belén Córdova Vásquez, Jhessica Coral Villca Palma, Karen Rocio Catari Calderón, Abigail Blanca Mamani Mamani]|
|05|grupo-05-springb|["Diana Cecilia Flores Chacón, Juan Sebastián Camacho Fernández, Andrés Wiliam Galarza Salguero, Harold Ruddy Quispe Hilari, José Alejandro Díaz Ali, Michelle Ruth Poma Ramos, Daron Augusto Baldiviezo Aillon"]|
|06|grupo-06-springb|[José Aruquipa, Miguel Calderón, Herlan Callisaya, Oscar Luján, Edith Marca, Luz Tinta, Daniel Zeballos]|
|07|grupo-07-springb|[Lenz Abad Alanoca Ojeda,Juan Vidal Mamani Riveros,Herlan Choque Flores,Lorgio Emilio Chura Carrillo,Jesús Alejandro Cruz,Juan Carlos Limachi Maydana]|
|08|grupo-08-springb|[Amílcar Josías Yujra Chipana, Luis Alfredo Quispe Ortiz, Alan Sergio Yupanqui Corini, Yehonatan Oscar Limachi Corina, Melany Abril Mamani Chamizo, Limbert Mamani Quiñajo, Ronald Choque Sillo]|
|09|grupo-09-springb|[Bautista Coaquira Jose Abraham, Laura Rios Lizbeth Fabiola, Penélope Gema Copana Fuentes, Sasha Johannes konrad Arana Ramirez, Callisaya Vargas Marco Ronaldo, Callisaya Lanes Shelly Anahi, Choque Gutiérrez Manuel Alejandro, Elías Daniel Beltrán Selaez]|
|10|grupo-10-springb|[María Teresa Aspiazu Sánchez, Jesús Abed Herrera Sirpa, Joel Alejandro Pérez Murillo, Ariadne Checcid Quiroz Coila, Brandom Jhoseff Amezaga Garrido Cael Mathew Cuevas Alconini, José Alfredo Choque Choque]|
|11|grupo-11-springb|[Israel Andrés Quenta Pomacusi, Edson Javier Mamani Ticona,Jhamil Elías Mamani Colque,Alexander Nataniel Castillo Centellas,Adrián Marcelo Requena Oros,Maritza Zárate Paco ,Jhoel Alexander Chipana Paye]|
|12|grupo-12-springb|[Víctor Bernardo Quispe Rojas,Gabriel Omar Cumara Patty,Cristian William Bautista Villcacuti,Rosa Katerine Gonzales Choque,Alvin Angel Magne Aruquipa,Blanca Nataly Chipana Orellana,Ronald Mendoza Caspa,José Julián Quinteros Mollinedo]|
|13|grupo-13-springb|[Quispe Adriana, Carvajal Ester, Tirado Nayheli, Canaviri Carlos, Loza Humberto, Mamani Sarahi, Ticona Alex]|
|14|grupo-14-springb|[Gutiérrez Challapa Daniel Rodrigo, Hidalgo Colque Ariana Daniela,Huanca Tito José Manuel,Mamani Mamani Mirko Sony,Quecaño Uruña Erika,Quiñajo Berrios Melina Viana]|
|15|grupo-15-springb|[Julio Picavia Saravia, Carlos Callisaya Rosas, Iver Mamani, Amiel Natanieli Méndez Vargas, Adriana Valeria Fernández Flores, Luz Edely Aruquipa Ururi]|
|16|Grupo-16-springb|[Miranda Aguirre Carlos Manuel, Tapia Cortez Genesis Jalid, Aarón Oswaldo Nina Calzada, Lucas Calderon, David Mamani, Rudy Ibarra, Julio Cesar Ticona, José Alejandro Fernández Sánchez ]|




---


## 📦 Cómo trabajar en tu rama

1. Clona el repositorio:
```bash
git clone https://github.com/LiaRos-ai/RegistroUniversitario.git
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
