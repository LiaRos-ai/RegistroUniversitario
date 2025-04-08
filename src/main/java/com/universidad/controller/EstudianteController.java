package com.universidad.controller; // Define el paquete al que pertenece esta clase

import com.universidad.dto.EstudianteDTO; // Importa la clase EstudianteDTO del paquete dto
import com.universidad.service.IEstudianteService; // Importa la interfaz IEstudianteService del paquete service

import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación Autowired de Spring
import org.springframework.http.ResponseEntity; // Importa la clase ResponseEntity de Spring para manejar respuestas HTTP
import org.springframework.web.bind.annotation.*; // Importa las anotaciones de Spring para controladores web

import java.util.List; // Importa la interfaz List para manejar listas

@RestController // Anotación que indica que esta clase es un controlador REST de Spring
@RequestMapping("/api") // Define la ruta base para las solicitudes HTTP a este controlador
public class EstudianteController { // Define la clase EstudianteController

    private final IEstudianteService estudianteService; // Declara una variable final para el servicio de estudiantes

    @Autowired // Anotación que indica que el constructor debe ser usado para inyección de dependencias
    public EstudianteController(IEstudianteService estudianteService) { // Constructor que recibe el servicio de estudiantes
        this.estudianteService = estudianteService; // Asigna el servicio de estudiantes a la variable de instancia
    }

    @GetMapping("/estudiantes") // Anotación que indica que este método maneja solicitudes GET
    public ResponseEntity<List<EstudianteDTO>> obtenerTodosLosEstudiantes() { // Método para obtener una lista de todos los EstudianteDTO
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodosLosEstudiantes(); // Llama al servicio para obtener todos los estudiantes
        return ResponseEntity.ok(estudiantes); // Retorna una respuesta HTTP 200 OK con la lista de estudiantes
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerEstudianteById(@PathVariable Long id) {
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorId(id);
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping("/crea-estudiante") // Anotación que indica que este método maneja solicitudes POST
    public ResponseEntity<EstudianteDTO> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO) { // Método para crear un nuevo estudiante
        EstudianteDTO nuevoEstudiante = estudianteService.crearEstudiante(estudianteDTO); // Llama al servicio para crear un nuevo estudiante
        return ResponseEntity.status(201).body(nuevoEstudiante); // Retorna una respuesta HTTP 201 Created con el nuevo estudiante
    }

    @PutMapping("/actualiza-estudiante/{id}") // Anotación que indica que este método maneja solicitudes PUT
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) { // Método para actualizar un estudiante existente
        EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, estudianteDTO); // Llama al servicio para actualizar el estudiante
        return ResponseEntity.ok(estudianteActualizado); // Retorna una respuesta HTTP 200 OK con el estudiante actualizado
    }
    
    @DeleteMapping("/elimina-estudiante/{id}") // Anotación que indica que este método maneja solicitudes DELETE
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) { // Método para eliminar un estudiante por su ID
        estudianteService.eliminarEstudiante(id); // Llama al servicio para eliminar el estudiante
        return ResponseEntity.noContent().build(); // Retorna una respuesta HTTP 204 No Content
    }

}