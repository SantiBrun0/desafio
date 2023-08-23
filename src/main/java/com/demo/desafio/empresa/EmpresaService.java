package com.demo.desafio.empresa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EmpresaService {

    private final EmpresaRepository repository;

    public List<Empresa> buscarTodos() {
        return repository.findAll();
    }

    public void agregarEmpresa(Empresa empresa) {
        validarEmpresa(empresa);
        repository.save(empresa);
    }

    public void eliminarEmpresa(int id) {
        repository.deleteById(id);
    }

    public void validarEmpresa(Empresa empresa) {
        if (repository.findByNombre(empresa.getNombre()) != null) throw new IllegalArgumentException("La empresa ya existe en la base de datos");
        if (empresa.getNombre() == null) throw new IllegalArgumentException("El atributo 'nombre' es obligatorio");
        if (empresa.getNombre().isEmpty()) throw new IllegalArgumentException("El atributo 'nombre' no puede estar vacío");
        if (empresa.getNroCuit() == null) throw new IllegalArgumentException("El atributo 'nroCuit' es obligatorio");
        if (empresa.getNroCuit().isEmpty()) throw new IllegalArgumentException("El atributo 'nroCuit' no puede estar vacío");
    }

}
