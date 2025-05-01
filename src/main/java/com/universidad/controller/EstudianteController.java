package com.universidad.controller;

import com.universidad.dto.EstudianteDTO;
import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
<<<<<<< HEAD
import com.universidad.service.IEstudianteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
=======
import com.universidad.model.Estudiante;
import com.universidad.service.IEstudianteService; // Importa la interfaz IEstudianteService del paquete service

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación Autowired de Spring
import org.springframework.http.ResponseEntity; // Importa la clase ResponseEntity de Spring para manejar respuestas HTTP
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus; // Importa la clase HttpStatus de Spring para manejar códigos de estado HTTP
import org.springframework.web.bind.annotation.*; // Importa las anotaciones de Spring para controladores web
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3

    private final IEstudianteService estudianteService;

<<<<<<< HEAD
    @Autowired
    public EstudianteController(IEstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> obtenerTodosLosEstudiantes() {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodosLosEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/inscripcion/{numeroInscripcion}")
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorNumeroInscripcion(@PathVariable String numeroInscripcion) {
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorNumeroInscripcion(numeroInscripcion);
        return ResponseEntity.ok(estudiante);
=======
@RestController // Anotación que indica que esta clase es un controlador REST de Spring
@RequestMapping("/api/estudiantes") // Define la ruta base para las solicitudes HTTP a este controlador
@Validated
public class EstudianteController { // Define la clase EstudianteController

    private final IEstudianteService estudianteService; // Declara una variable final para el servicio de estudiantes
    private static final Logger logger = LoggerFactory.getLogger(EstudianteController.class);

    @Autowired // Anotación que indica que el constructor debe ser usado para inyección de dependencias
    public EstudianteController(IEstudianteService estudianteService) { // Constructor que recibe el servicio de estudiantes
        this.estudianteService = estudianteService; // Asigna el servicio de estudiantes a la variable de instancia
    }

    @GetMapping // Anotación que indica que este método maneja solicitudes GET
    public ResponseEntity<List<EstudianteDTO>> obtenerTodosLosEstudiantes() { // Método para obtener una lista de todos los EstudianteDTO
        long inicio = System.currentTimeMillis();
        logger.info("[ESTUDIANTE] Inicio obtenerTodosLosEstudiantes: {}", inicio);
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodosLosEstudiantes(); // Llama al servicio para obtener todos los estudiantes
        long fin = System.currentTimeMillis();
        logger.info("[ESTUDIANTE] Fin obtenerTodosLosEstudiantes: {} (Duracion: {} ms)", fin, (fin-inicio));
        return ResponseEntity.ok(estudiantes); // Retorna una respuesta HTTP 200 OK con la lista de estudiantes
    }

    @GetMapping("/inscripcion/{numeroInscripcion}") // Anotación que indica que este método maneja solicitudes GET con un parámetro de ruta
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorNumeroInscripcion(
        @PathVariable String numeroInscripcion) { // Método para obtener un estudiante por su número de inscripción
        long inicio = System.currentTimeMillis();
        logger.info("[ESTUDIANTE] Inicio obtenerEstudiantePorNumeroInscripcion: {}", inicio);
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorNumeroInscripcion(numeroInscripcion); // Llama al servicio para obtener el estudiante
        long fin = System.currentTimeMillis();
        logger.info("[ESTUDIANTE] Fin obtenerEstudiantePorNumeroInscripcion: {} (Duracion: {} ms)", fin, (fin-inicio));
        return ResponseEntity.ok(estudiante); // Retorna una respuesta HTTP 200 OK con el estudiante encontrado
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
    }

    @GetMapping("/{id}/materias")
    public ResponseEntity<List<Materia>> obtenerMateriasDeEstudiante(
        @PathVariable("id") Long estudianteId) {
        List<Materia> materias = estudianteService.obtenerMateriasDeEstudiante(estudianteId);
        return ResponseEntity.ok(materias);
    }

<<<<<<< HEAD
    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EstudianteDTO> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO nuevoEstudiante = estudianteService.crearEstudiante(estudianteDTO);
        return ResponseEntity.status(201).body(nuevoEstudiante);
    }

    @PutMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, estudianteDTO);
        return ResponseEntity.ok(estudianteActualizado);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EstudianteDTO>> obtenerEstudianteActivo() {
        List<EstudianteDTO> estudiantesActivos = estudianteService.obtenerEstudianteActivo();
        return ResponseEntity.ok(estudiantesActivos);
=======
    @GetMapping("/{id}/lock")
    public ResponseEntity<Estudiante> getEstudianteConBloqueo(
        @PathVariable Long id) {
        Estudiante estudiante = estudianteService.obtenerEstudianteConBloqueo(id);
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping // Anotación que indica que este método maneja solicitudes POST
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.CREATED) // Anotación que indica que la respuesta HTTP debe tener un estado 201 Created
    public ResponseEntity<EstudianteDTO> crearEstudiante(@Valid @RequestBody EstudianteDTO estudianteDTO) { // Método para crear un nuevo estudiante
        EstudianteDTO nuevoEstudiante = estudianteService.crearEstudiante(estudianteDTO); // Llama al servicio para crear el estudiante
        return ResponseEntity.status(201).body(nuevoEstudiante); // Retorna una respuesta HTTP 201 Created con el nuevo estudiante
    }

    @PutMapping("/{id}") // Anotación que indica que este método maneja solicitudes PUT con un parámetro de ruta
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK) // Anotación que indica que la respuesta HTTP debe tener un estado 200 OK    
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(
        @PathVariable Long id,
        @RequestBody EstudianteDTO estudianteDTO) { // Método para actualizar un estudiante existente
        EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, estudianteDTO); // Llama al servicio para actualizar el estudiante
        return ResponseEntity.ok(estudianteActualizado); // Retorna una respuesta HTTP 200 OK con el estudiante actualizado
    }

    @PutMapping("/{id}/baja") // Anotación que indica que este método maneja solicitudes PUT para dar de baja un estudiante
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK) // Anotación que indica que la respuesta HTTP debe tener un estado 200 OK
    public ResponseEntity<EstudianteDTO> eliminarEstudiante(
        @PathVariable Long id,
        @RequestBody EstudianteDTO estudianteDTO) { // Método para eliminar un estudiante
        EstudianteDTO estudianteEliminado = estudianteService.eliminarEstudiante(id, estudianteDTO); // Llama al servicio para eliminar el estudiante
        return ResponseEntity.ok(estudianteEliminado); // Retorna una respuesta HTTP 200 OK con el estudiante eliminado
>>>>>>> e9e36e5ae9530c3f8ada58a470f45ab7dee40de3
    }

    @PutMapping("/eliminar/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EstudianteDTO> eliminarId(@PathVariable Long id) {
        EstudianteDTO estudianteActualizado = estudianteService.eliminarId(id);
        return ResponseEntity.ok(estudianteActualizado);
    }


    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<EstudianteDTO> buscarPorNombre(@PathVariable String nombre){
        System.out.println("Entro a buscar");
        EstudianteDTO estudianteDTO = estudianteService.buscarPorNombre(nombre);
        if(estudianteDTO == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(estudianteDTO);
        }
    }
    @GetMapping("/ordenar")
    public ResponseEntity<List<EstudianteDTO>> mostrarOrdenado() {
        List<EstudianteDTO> estudiantes = estudianteService.ordenarPorApellido();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/materias/{id}")
    public ResponseEntity<List<MateriaDTO>> mostrarEstudianteConInscripciones(@PathVariable Long id){
        List<MateriaDTO> materiaDTOS = estudianteService.materiasInscritasSegunId(id);
        return ResponseEntity.ok(materiaDTOS);
    }
}

