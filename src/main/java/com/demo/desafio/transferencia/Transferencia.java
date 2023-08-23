package com.demo.desafio.transferencia;

import com.demo.desafio.banco.Banco;
import com.demo.desafio.pago.Pago;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Transferencia extends Pago {

    private int numero;

    @ManyToOne
    private Banco bancoOrigen;

    @ManyToOne
    private Banco bancoDestino;

}
