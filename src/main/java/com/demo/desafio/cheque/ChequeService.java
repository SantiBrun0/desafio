package com.demo.desafio.cheque;

import com.demo.desafio.estado.Estado;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ChequeService {

    private final ChequeRepository repository;

    public List<Cheque> buscarTodos() {
        return repository.findAll();
    }

    public void agregarCheque(Cheque cheque) {
        validarCheque(cheque);
        repository.save(cheque);
    }

    public void eliminarCheque(int id) {
        repository.deleteById(id);
    }

    public void modificarEstadoCheque(int numero, String estado) {
        Cheque cheque = repository.findByNumero(numero);

        if (cheque == null) throw new IllegalArgumentException("El cheque no existe en la base de datos");

        try {
            Estado estadoEnum = Estado.valueOf(estado.toUpperCase().trim());

            if (cheque.getEstado() != estadoEnum) {
                cheque.setEstado(estadoEnum);

                if (estadoEnum == Estado.DEPOSITADO) {
                    cheque.setFechaDepositado(new Date());
                } else if (estadoEnum == Estado.COBRADO) {
                    cheque.setFechaCobrado(new Date());
                } else if (estadoEnum == Estado.DEVUELTO) {
                    cheque.setFechaDevuelto(new Date());
                }

                repository.save(cheque);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ingrese un estado válido: " + Arrays.toString(Estado.values()));
        }
    }

    public void validarCheque(Cheque cheque) {
        if (repository.findByNumero(cheque.getNumero()) != null) throw new IllegalArgumentException("El cheque ya existe en la base de datos");

        if (cheque.getFecha() == null ||
                cheque.getMonto() <= 0 ||
                cheque.getEmpresaOrigen() == null ||
                cheque.getEmpresaDestino() == null ||
                cheque.getNumero() <= 0 ||
                cheque.getBanco() == null) {
            throw new IllegalArgumentException("Existen datos incompletos o incorrectos, por favor revisar");
        }
    }

}
