package com.demo.desafio.banco;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BancoServiceTest {

    @Mock
    private BancoRepository bancoRepository;

    @InjectMocks
    private BancoService bancoService;

    public BancoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("WHEN se buscan bancos y no existe ninguno THEN se retorna una lista vacia")
    void buscarTodos_DebeRetornarListaVacia_CuandoNoHayBancos() {
        when(bancoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Banco> bancos = bancoService.buscarTodos();

        assert(bancos.isEmpty());
    }

    @Test
    @DisplayName("WHEN se agrega un banco con nombre nullo THEN se lanza excepcion")
    void agregarBanco_DebeLanzarExcepcion_CuandoNombreEsNulo() {
        Banco banco = new Banco();
        banco.setNombre(null);

        assertThrows(IllegalArgumentException.class, () -> bancoService.agregarBanco(banco));

        verify(bancoRepository, never()).save(banco);
    }

    @Test
    @DisplayName("WHEN se agrega un banco con nombre vacio THEN se lanza excepcion")
    void agregarBanco_DebeLanzarExcepcion_CuandoNombreEstaVacio() {
        Banco banco = new Banco();
        banco.setNombre("");

        assertThrows(IllegalArgumentException.class, () -> bancoService.agregarBanco(banco));

        verify(bancoRepository, never()).save(banco);
    }

    @Test
    @DisplayName("WHEN se agrega un banco que ya existe THEN se lanza excepcion")
    void agregarBanco_DebeLanzarExcepcion_CuandoBancoYaExiste() {
        Banco banco = new Banco();
        banco.setNombre("Banco existente");

        when(bancoRepository.findByNombre(banco.getNombre())).thenReturn(banco);

        assertThrows(IllegalArgumentException.class, () -> bancoService.agregarBanco(banco));

        verify(bancoRepository, never()).save(banco);
    }

    @Test
    @DisplayName("WHEN se agrega un banco con datos correctos THEN se persiste en la base de datos")
    void agregarBanco_DebeGuardarBanco_CuandoEsValido() {
        Banco banco = new Banco();
        banco.setNombre("Banco nuevo");

        when(bancoRepository.findByNombre(banco.getNombre())).thenReturn(null);

        bancoService.agregarBanco(banco);

        verify(bancoRepository).save(banco);
    }

    @Test
    @DisplayName("WHEN se elimina un banco THEN se deja de persistir en la base de datos")
    void eliminarBanco_DebeLlamarMetodoDeleteById_CuandoSeInvoca() {
        int id = 1;

        bancoService.eliminarBanco(id);

        verify(bancoRepository).deleteById(id);
    }
}