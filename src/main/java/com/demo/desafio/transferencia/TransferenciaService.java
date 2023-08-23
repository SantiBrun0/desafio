package com.demo.desafio.transferencia;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TransferenciaService {

    private final TransferenciaRepository repository;

    public List<Transferencia> buscarTodos() {
        return repository.findAll();
    }

    public void agregarTransferencia(Transferencia transferencia) {
        validarTransferencia(transferencia);
        repository.save(transferencia);
    }

    public void eliminarTransferencia(int id) {
        repository.deleteById(id);
    }

    public void validarTransferencia(Transferencia transferencia) {
        if (repository.findByNumero(transferencia.getNumero()) != null) throw new IllegalArgumentException("La transferencia ya existe en la base de datos");
        if (transferencia.getFecha() == null ||
            transferencia.getMonto() <= 0 ||
            transferencia.getEmpresaOrigen() == null ||
            transferencia.getEmpresaDestino() == null ||
            transferencia.getNumero() <= 0 ||
            transferencia.getBancoOrigen() == null ||
            transferencia.getBancoDestino() == null) {
            throw new IllegalArgumentException("Existen datos incompletos o incorrectos, por favor revisar");
        }
    }

}
