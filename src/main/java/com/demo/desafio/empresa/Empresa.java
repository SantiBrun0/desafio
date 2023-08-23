package com.demo.desafio.empresa;

import com.demo.desafio.tipoIva.TipoIva;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String razonSocial;
    private String nroCuit;

    @Enumerated(EnumType.STRING)
    private TipoIva tipoIva;

}
