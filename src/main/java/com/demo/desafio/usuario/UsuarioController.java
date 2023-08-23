package com.demo.desafio.usuario;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping("/{usuario}")
    public ResponseEntity<Optional<Usuario>> buscarTodos(@PathVariable String usuario) {
        return ResponseEntity.ok(service.buscarUsuario(usuario));
    }

}
