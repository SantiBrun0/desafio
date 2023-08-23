package com.demo.desafio.pago;

import com.demo.desafio.empresa.Empresa;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date fecha;
    private double monto;

    @ManyToOne
    private Empresa empresaOrigen;

    @ManyToOne
    private Empresa empresaDestino;
}
