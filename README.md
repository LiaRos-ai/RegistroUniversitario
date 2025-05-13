<<<<<<< HEAD
# Investigación: Uso de Optional en Java

## ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
`Optional` es una clase contenedor introducida en Java 8 que representa un valor que puede estar presente o no (es decir, opcional). Se recomienda su uso para evitar errores comunes como el `NullPointerException`, y para hacer explícito que un valor puede ser nulo, mejorando así la legibilidad y robustez del código.

## ¿Cuál es la diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()?
- `Optional.empty()`: Devuelve un `Optional` vacío (sin valor).
- `Optional.of(value)`: Crea un `Optional` con un valor no nulo. Lanza `NullPointerException` si el valor es `null`.
- `Optional.ofNullable(value)`: Crea un `Optional` que puede contener un valor nulo o no. Si el valor es `null`, devuelve un `Optional.empty()`.

## ¿Qué ventajas tiene Optional frente a regresar null?
- Elimina el riesgo de `NullPointerException`.
- Obliga al programador a manejar explícitamente los valores ausentes.
- Mejora la legibilidad del código al indicar claramente que un valor puede no estar presente.
- Facilita operaciones funcionales como `map()`, `filter()` y `ifPresent()`.

## ¿Cómo se integra Optional en Spring Data JPA?
Spring Data JPA permite usar `Optional` como tipo de retorno en los métodos de repositorios. Por ejemplo:
```java
Optional<Usuario> findByEmail(String email);
```
Esto permite trabajar de manera más segura con posibles valores nulos que vienen de la base de datos, evitando excepciones innecesarias.

## ¿Qué método de Optional permite lanzar una excepción si no hay resultado?
El método `orElseThrow()` lanza una excepción si el valor no está presente. Se puede usar con una lambda para lanzar una excepción personalizada:
```java
usuarioOptional.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
```

## ¿Por qué es útil Optional en el contexto de una API REST?
En una API REST, `Optional` permite manejar adecuadamente los recursos no encontrados (por ejemplo, con `404 Not Found`) en vez de retornar `null`, lo que mejora la claridad del código y facilita el control de errores y las respuestas HTTP apropiadas.

---

**Integrantes del grupo:**
- Brandon Jhosef Amezaga Garrido
- Maria Teresa Aspiazu Sanches
- Jesus Abed Herrera Sirpa
- Joel Alejandro Perez Murillo
- Ariadne Checcid Quiroz Coila
- Cael Mathew Cuevas Alconini
- Jose Alfredo Choque Choque
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
>>>>>>> main
