package com.demo.desafio.cheque;

import com.demo.desafio.banco.Banco;
import com.demo.desafio.empresa.Empresa;
import com.demo.desafio.estado.Estado;
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

class ChequeServiceTest {

    @Mock
    private ChequeRepository chequeRepository;

    @InjectMocks
    private ChequeService chequeService;

    public ChequeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("WHEN se buscan cheques y no existe ninguno THEN se retorna una lista vacia")
    void buscarTodos_DebeRetornarListaVacia_CuandoNoHayCheques() {
        when(chequeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Cheque> cheques = chequeService.buscarTodos();

        assertTrue(cheques.isEmpty());
    }

    @Test
    @DisplayName("WHEN se agrega un cheque con datos incompletos THEN se lanza excepcion")
    void agregarCheque_DebeLanzarExcepcion_CuandoDatosIncompletos() {
        Cheque cheque = new Cheque();

        assertThrows(IllegalArgumentException.class, () -> chequeService.agregarCheque(cheque));

        verify(chequeRepository, never()).save(cheque);
    }

    @Test
    @DisplayName("WHEN se agrega un cheque con datos validos THEN se persiste en la base de datos")
    void agregarCheque_DebeLlamarMetodoSave_CuandoDatosSonValidos() {
        var tipoIva = TipoIva.TASA_GENERAL;
        Empresa empresaA = new Empresa(1, "Empresa A", "Razon A", "Cuit A", tipoIva);
        Empresa empresaB = new Empresa(2, "Empresa B", "Razon B", "Cuit B", tipoIva);
        Banco banco = new Banco(1, "Banco C");
        Cheque cheque = new Cheque();
        cheque.setFecha(new Date());
        cheque.setMonto(100);
        cheque.setEmpresaOrigen(empresaA);
        cheque.setEmpresaDestino(empresaB);
        cheque.setNumero(123);
        cheque.setBanco(banco);

        chequeService.agregarCheque(cheque);

        verify(chequeRepository).save(cheque);
    }

    @Test
    @DisplayName("WHEN se eliminar un cheque THEN se deja de persistir en la base de datos")
    void eliminarCheque_DebeLlamarMetodoDeleteById_CuandoSeInvoca() {
        int id = 1;

        chequeService.eliminarCheque(id);

        verify(chequeRepository).deleteById(id);
    }

    @Test
    @DisplayName("WHEN se modifica un cheque con estado valido THEN debe cambiar estado y fecha pertinente")
    void modificarEstadoCheque_DebeCambiarEstadoYFechas_CuandoEstadoValido() {
        int numero = 123;
        String estado = "depositado";

        Cheque cheque = new Cheque();
        cheque.setNumero(numero);
        cheque.setEstado(Estado.RECIBIDO);

        when(chequeRepository.findByNumero(numero)).thenReturn(cheque);

        chequeService.modificarEstadoCheque(numero, estado);

        assertEquals(Estado.DEPOSITADO, cheque.getEstado());
        assertNotNull(cheque.getFechaDepositado());
    }

    @Test
    @DisplayName("WHEN se modifica un cheque con estado invalido THEN se lanza excepcion")
    void modificarEstadoCheque_DebeLanzarExcepcion_CuandoEstadoNoValido() {
        int numero = 123;
        String estado = "estado_invalido";

        Cheque cheque = new Cheque();
        cheque.setNumero(numero);

        when(chequeRepository.findByNumero(numero)).thenReturn(cheque);

        assertThrows(IllegalArgumentException.class, () -> chequeService.modificarEstadoCheque(numero, estado));
    }
}