package com.demo.desafio.banco;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/banco")
public class BancoController {

    private final BancoService service;

    @GetMapping
    public ResponseEntity<List<Banco>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> agregarBanco(@RequestBody Banco banco) {
        Map<String, String> response = new HashMap<>();
        try {
            service.agregarBanco(banco);
            response.put("INFO", "Banco agregado con éxito a la base de datos");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("ERROR", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarBanco(@PathVariable int id) {
        service.eliminarBanco(id);
        Map<String, String> response = new HashMap<>();
        response.put("INFO", "Se eliminó el banco de la base de datos");
        return ResponseEntity.ok(response);
    }

}
