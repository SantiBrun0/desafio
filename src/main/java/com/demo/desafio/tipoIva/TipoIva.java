package com.demo.desafio.tipoIva;

public enum TipoIva {
    TASA_GENERAL(21),
    TASA_REDUCIDA(10),
    TASA_ELEVADA(30);

    private final int porcentaje;

    TipoIva(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

}
