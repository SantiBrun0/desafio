package com.demo.desafio.transferencia;

import com.demo.desafio.banco.Banco;
import com.demo.desafio.empresa.Empresa;
import com.demo.desafio.tipoIva.TipoIva;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @InjectMocks
    private TransferenciaService transferenciaService;

    public TransferenciaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("WHEN se buscan transferencias y no existe ninguna THEN se retorna una lista vacia")
    void buscarTodos_DebeRetornarListaVacia_CuandoNoHayTransferencias() {
        when(transferenciaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Transferencia> transferencias = transferenciaService.buscarTodos();

        assertTrue(transferencias.isEmpty());
    }

    @Test
    @DisplayName("WHEN se agrega transferencia con datos incompletos THEN se lanza excepcion")
    void agregarTransferencia_DebeLanzarExcepcion_CuandoDatosIncompletos() {
        Transferencia transferencia = new Transferencia();

        assertThrows(IllegalArgumentException.class, () -> transferenciaService.agregarTransferencia(transferencia));

        verify(transferenciaRepository, never()).save(transferencia);
    }

    @Test
    @DisplayName("WHEN se agrega transferencia con datos validos THEN se persiste en la base de datos")
    void agregarTransferencia_DebeLlamarMetodoSave_CuandoDatosSonValidos() {
        var tipoIva = TipoIva.TASA_GENERAL;
        Empresa empresaA = new Empresa(1, "Empresa A", "Razon A", "Cuit A", tipoIva);
        Empresa empresaB = new Empresa(2, "Empresa B", "Razon B", "Cuit B", tipoIva);
        Banco bancoC = new Banco(1, "Banco C");
        Banco bancoD = new Banco(2, "Banco D");
        Transferencia transferencia = new Transferencia();
        transferencia.setFecha(new Date());
        transferencia.setMonto(100);
        transferencia.setEmpresaOrigen(empresaA);
        transferencia.setEmpresaDestino(empresaB);
        transferencia.setNumero(123);
        transferencia.setBancoOrigen(bancoC);
        transferencia.setBancoDestino(bancoD);

        transferenciaService.agregarTransferencia(transferencia);

        verify(transferenciaRepository).save(transferencia);
    }

    @Test
    @DisplayName("WHEN se elimina una transferencia THEN se deja de persistir en la base de datos")
    void eliminarTransferencia_DebeLlamarMetodoDeleteById_CuandoSeInvoca() {
        int id = 1;

        transferenciaService.eliminarTransferencia(id);

        verify(transferenciaRepository).deleteById(id);
    }

}
