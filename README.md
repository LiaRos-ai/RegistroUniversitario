# Grupo 4 - Proyecto Gestion Universidad

Este proyecto es una API para gestionar estudiantes y materias. A continuación se documenta una operación implementada recientemente:

Investigación sobre Optional en Java
1. ¿Qué es Optional en Java? ¿Por qué se recomienda su uso?
Optional es una clase que funciona como un contenedor que puede o no contener un valor no nulo. Se recomienda su uso porque reduce el riesgo de NullPointerException y obliga al desarrollador a manejar de forma segura el caso en que el valor está ausente.

2. Diferencia entre Optional.empty(), Optional.of(), y Optional.ofNullable()
Optional.empty(): Crea un Optional que no contiene ningún valor. Se utiliza cuando se quiere representar explícitamente la ausencia de un valor.

Ejemplo:

Optional<String> emptyOptional = Optional.empty();
Optional.of(T value): Crea un Optional que contiene el valor proporcionado. Si el valor es null, Optional.of() arrojará una NullPointerException. Se utiliza cuando se tiene la certeza de que el valor no es null.

Ejemplo:

Optional<String> optionalWithText = Optional.of("Hola");
Optional.ofNullable(T value): Crea un Optional que contiene el valor si no es null, o un Optional vacío si el valor es null. Se utiliza cuando el valor que se va a envolver en un Optional podría ser null.

Ejemplo:

Optional<String> optionalWithText = Optional.ofNullable("Hola"); // Creará un Optional con "Hola"
3. ¿Qué ventajas tiene Optional frente a regresar null?
Usar Optional en lugar de retornar null en Java ofrece varias ventajas:

Evita errores comunes como el NullPointerException.

Mejora la legibilidad del código al dejar claro que un valor puede estar ausente, y obliga al desarrollador a manejar esa ausencia de forma explícita.

Integración con la programación funcional: Optional facilita el uso de métodos como .map(), .orElse() o .ifPresent().

4. ¿Cómo se integra Optional en Spring Data JPA?
En Spring Data JPA, Optional se utiliza como tipo de retorno en los métodos de los repositorios para indicar que un resultado puede estar presente o no. Por ejemplo, findById(Long id) devuelve un Optional<T>, lo que evita el uso de null y permite manejar de forma segura la ausencia de datos.

5. ¿Qué método de Optional permite lanzar una excepción si no hay resultado?
orElseThrow(): Este método lanza una excepción si el Optional está vacío.

Ejemplo:

Estudiante estudiante = estudianteOptional.orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
6. ¿Por qué es útil Optional en el contexto de una API REST?
Optional es útil en el contexto de una API REST porque ayuda a evitar errores por null y te permite manejar mejor los casos donde no hay datos. Esto se traduce en devolver un error 404 si no se encuentra un recurso.

## 1. Obtener Materias por Estudiante (Usando Optional)

Esta operación permite obtener la lista de materias asociadas a un estudiante específico mediante su ID. La implementación utiliza Optional para manejar de forma segura los posibles valores nulos y listas vacías.

1. Función en el Repositorio (EstudianteRepository.java):

Optional<Estudiante> findById(Long id);  // Método para obtener un estudiante por su ID
Explicación: El repositorio usa Optional<Estudiante> para evitar problemas de null al buscar un estudiante por su ID.

2. Función en la Interfaz del Servicio (IEstudianteService.java):

Optional<List<Materia>> obtenerMateriasDeEstudiante(Long estudianteId);
Explicación: El método define que se devolverá un Optional que puede contener la lista de materias asociadas al estudiante.

3. Función en la Implementación del Servicio (EstudianteServiceImpl.java):

@Override
public Optional<List<Materia>> obtenerMateriasDeEstudiante(Long estudianteId) {
    Optional<Estudiante> estudianteOptional = estudianteRepository.findById(estudianteId);
    return estudianteOptional.map(Estudiante::getMaterias); // Devuelve las materias si el estudiante existe
}
Explicación: En la implementación, se usa findById para obtener el estudiante de manera segura. Luego, map se utiliza para obtener las materias del estudiante si este existe.

4. Función en el Controlador (EstudianteController.java):

@GetMapping("/{id}/materias")
public ResponseEntity<?> obtenerMateriasDeEstudiante(@PathVariable("id") Long estudianteId) {
    Optional<List<Materia>> materiasOptional = estudianteService.obtenerMateriasDeEstudiante(estudianteId);
    return materiasOptional.map(materias -> {
                if (materias.isEmpty()) {
                    return ResponseEntity.ok("El estudiante no tiene materias asignadas.");
                } else {
                    return ResponseEntity.ok().body(materias);
                }
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
}
Explicación: El controlador maneja la solicitud GET y verifica si el estudiante tiene materias asignadas. Si la lista de materias está vacía, devuelve un mensaje apropiado, y si el estudiante no se encuentra, retorna un error 404.