package com.demo.desafio.transferencia;

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
@RequestMapping("/transferencia")
public class TransferenciaController {

    private final TransferenciaService service;
    private final BancoRepository bancoRepository;
    private final EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<List<Transferencia>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> agregarTransferencia(@RequestBody Transferencia transferencia) {
        Map<String, String> response = new HashMap<>();

        var empresaOrigen = empresaRepository.findByNombre(transferencia.getEmpresaOrigen().getNombre());
        transferencia.setEmpresaOrigen(empresaOrigen);

        var empresaDestino = empresaRepository.findByNombre(transferencia.getEmpresaDestino().getNombre());
        transferencia.setEmpresaDestino(empresaDestino);

        var bancoOrigen = bancoRepository.findByNombre(transferencia.getBancoOrigen().getNombre());
        transferencia.setBancoOrigen(bancoOrigen);

        var bancoDestino = bancoRepository.findByNombre(transferencia.getBancoDestino().getNombre());
        transferencia.setBancoDestino(bancoDestino);

        try {
            service.agregarTransferencia(transferencia);
            response.put("INFO", "Transferencia agregada con éxito a la base de datos");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("ERROR", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
