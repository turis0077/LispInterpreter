package com.lispinterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase LispEnvironment
 * Se valida el correcto almacenamiento y recuperación de variables y funciones.
 */
public class TestLispEnvironment {

    /**
     * Verifica que se pueda guardar y obtener una variable correctamente.
     */
    @Test
    public void testGuardarYObtenerVariable() {
        LispEnvironment entorno = new LispEnvironment();
        entorno.guardarVariable("x", 42);
        assertEquals(42, entorno.obtenerVariable("x"));
    }

    /**
     * Verifica que la existencia de una variable sea detectada correctamente.
     */
    @Test
    public void testExisteVariable() {
        LispEnvironment entorno = new LispEnvironment();
        entorno.guardarVariable("y", 100);
        assertTrue(entorno.existeVariable("y"));
    }

    /**
     * Verifica que al intentar obtener una variable no definida se lance una excepción.
     */
    @Test
    public void testObtenerVariableNoDefinida() {
        LispEnvironment entorno = new LispEnvironment();
        entorno.obtenerVariable("z");
    }

    /**
     * Verifica que se pueda guardar y obtener una función correctamente.
     */
    @Test
    public void testGuardarYObtenerFuncion() {
        LispEnvironment entorno = new LispEnvironment();
        // Se crea una función dummy cuyo cuerpo es el número 123.
        LispFunction funcion = new LispFunction("dummy", java.util.Collections.emptyList(), 123);
        entorno.guardarFuncion("dummy", funcion);
        assertEquals(funcion, entorno.obtenerFuncion("dummy"));
    }

    /**
     * Verifica que al intentar obtener una función no definida se lance una excepción.
     */
    @Test
    public void testObtenerFuncionNoDefinida() {
        LispEnvironment entorno = new LispEnvironment();
        entorno.obtenerFuncion("nonexistent");
    }
}
