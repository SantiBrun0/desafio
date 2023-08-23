package com.demo.desafio.empresa;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService service;

    @GetMapping
    public ResponseEntity<List<Empresa>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> agregarEmpresa(@RequestBody Empresa empresa) {
        Map<String, String> response = new HashMap<>();
        try {
            service.agregarEmpresa(empresa);
            response.put("INFO", "Empresa agregado con éxito a la base de datos");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("ERROR", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarEmpresa(@PathVariable int id) {
        service.eliminarEmpresa(id);
        Map<String, String> response = new HashMap<>();
        response.put("INFO", "Se eliminó la empresa de la base de datos");
        return ResponseEntity.ok(response);
    }

}
