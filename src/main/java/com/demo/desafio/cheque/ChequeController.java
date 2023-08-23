package com.demo.desafio.cheque;

import com.demo.desafio.banco.BancoRepository;
import com.demo.desafio.empresa.EmpresaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/cheque")
public class ChequeController {

    private final ChequeService service;

    private final BancoRepository bancoRepository;

    private final EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<List<Cheque>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> agregarCheque(@RequestBody Cheque cheque) {
        Map<String, String> response = new HashMap<>();

        var empresaOrigen = empresaRepository.findByNombre(cheque.getEmpresaOrigen().getNombre());
        cheque.setEmpresaOrigen(empresaOrigen);

        var empresaDestino = empresaRepository.findByNombre(cheque.getEmpresaDestino().getNombre());
        cheque.setEmpresaDestino(empresaDestino);

        var banco = bancoRepository.findByNombre(cheque.getBanco().getNombre());
        cheque.setBanco(banco);

        try {
            service.agregarCheque(cheque);
            response.put("INFO", "Cheque agregado con éxito a la base de datos");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("ERROR", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> modificarEstadoCheque(@RequestParam int numero, @RequestParam String estado) {
        Map<String, String> response = new HashMap<>();
        try {
            service.modificarEstadoCheque(numero, estado);
            response.put("INFO", "Estado del cheque modificado con exito a: " + estado);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("ERROR", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarCheque(@PathVariable int id) {
        service.eliminarCheque(id);
        Map<String, String> response = new HashMap<>();
        response.put("INFO", "Se eliminó el cheque de la base de datos");
        return ResponseEntity.ok(response);
    }

}
