package com.demo.desafio.banco;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class BancoService {

    private final BancoRepository repository;

    public List<Banco> buscarTodos() {
        return repository.findAll();
    }

    public void agregarBanco(Banco banco) {
        validarBanco(banco);
        repository.save(banco);
    }

    public void eliminarBanco(int id) {
        repository.deleteById(id);
    }

    public void validarBanco(Banco banco) {
        if (repository.findByNombre(banco.getNombre()) != null) throw new IllegalArgumentException("El banco ya existe en la base de datos");
        if (banco.getNombre() == null) throw new IllegalArgumentException("El atributo 'nombre' es obligatorio");
        if (banco.getNombre().isEmpty()) throw new IllegalArgumentException("El atributo 'nombre' no puede estar vacío");
    }

}
