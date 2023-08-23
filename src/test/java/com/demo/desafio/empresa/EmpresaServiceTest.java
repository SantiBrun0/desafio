package com.demo.desafio.empresa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaService empresaService;

    public EmpresaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("WHEN se buscan empresas y no existe ninguna THEN se retorna una lista vacia")
    void buscarTodos_DebeRetornarListaVacia_CuandoNoHayEmpresas() {
        when(empresaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Empresa> empresas = empresaService.buscarTodos();

        assertTrue(empresas.isEmpty());
    }

    @Test
    @DisplayName("WHEN se agrega empresa con datos incompletos THEN se lanza excepcion")
    void agregarEmpresa_DebeLanzarExcepcion_CuandoDatosIncompletos() {
        Empresa empresa = new Empresa();

        assertThrows(IllegalArgumentException.class, () -> empresaService.agregarEmpresa(empresa));

        verify(empresaRepository, never()).save(empresa);
    }

    @Test
    @DisplayName("WHEN se agrega empresa con datos validos THEN se persiste en la base de datos")
    void agregarEmpresa_DebeLlamarMetodoSave_CuandoDatosSonValidos() {
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa A");
        empresa.setNroCuit("12345678901");

        empresaService.agregarEmpresa(empresa);

        verify(empresaRepository).save(empresa);
    }

    @Test
    @DisplayName("WHEN se eliminar una empresa THEN se deja de persistir en la base de datos")
    void eliminarEmpresa_DebeLlamarMetodoDeleteById_CuandoSeInvoca() {
        int id = 1;

        empresaService.eliminarEmpresa(id);

        verify(empresaRepository).deleteById(id);
    }

}




