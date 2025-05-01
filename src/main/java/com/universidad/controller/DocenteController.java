package com.universidad.controller;
import com.universidad.dto.DocenteDTO;
import com.universidad.service.IDocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/docente")
public class DocenteController {
    private final IDocenteService docenteService;
    @Autowired
    public DocenteController(IDocenteService docenteService){
        this.docenteService = docenteService;
    }

    @GetMapping
    public ResponseEntity<List<DocenteDTO>> obtenerDocentes(){
        List<DocenteDTO> docentes = docenteService.obtenerTodosLosDocentes();
        return ResponseEntity.ok(docentes);
    }

    @GetMapping("/{departamento}")
    public ResponseEntity<List<DocenteDTO>> obtenerSegunDepartamento(@PathVariable String departamento){
        List<DocenteDTO> docentes = docenteService.obtenerTodosLosDocentesSegunDepartamento(departamento);
        return ResponseEntity.ok(docentes);
    }


}
