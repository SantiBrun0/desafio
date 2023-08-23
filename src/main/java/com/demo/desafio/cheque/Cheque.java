package com.demo.desafio.cheque;

import com.demo.desafio.banco.Banco;
import com.demo.desafio.estado.Estado;
import com.demo.desafio.pago.Pago;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cheque extends Pago {

    private int numero;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.RECIBIDO;

    @ManyToOne
    private Banco banco;

    private Date fechaDepositado;
    private Date fechaCobrado;
    private Date fechaDevuelto;

}
