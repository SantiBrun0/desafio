package com.demo.desafio.usuario;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public Optional<Usuario> buscarUsuario(String usuario) {
        return repository.findByUsuario(usuario);
    }

}
