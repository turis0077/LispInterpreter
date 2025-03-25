package com.lispinterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase LispBuiltinFunctions
 * Se evalúan las funciones integradas que realizan operaciones aritméticas y predicados.
 */
public class TestLispBuiltinFunctions {
    /**
     * Verifica que la funcion sumar retorne la suma correcta de los numeros.
     */
    @Test
    public void testSumar() {
        List<Object> argumentos = Arrays.asList(1, 2, 3, 4);
        int resultado = LispBuiltinFunctions.sumar(argumentos);
        assertEquals(10, resultado);
    }

    /**
     * Verifica que la funcino resta realice la resta secuencial de forma correcta.
     */
    @Test
    public void testRestar() {
        List<Object> argumentos = Arrays.asList(10, 3, 2);
        int resultado = LispBuiltinFunctions.restar(argumentos);
        assertEquals(5, resultado);
    }

    /**
     * Verifica que la función multiplicar retorne el producto correcto.
     */
    @Test
    public void testMultiplicar() {
        List<Object> argumentos = Arrays.asList(2, 3, 4);
        int resultado = LispBuiltinFunctions.multiplicar(argumentos);
        assertEquals(24, resultado);
    }

    /**
     * Verifica que la funcion compararMenor retorne true cuando el primer argumento es menor que el segundo.
     */
    @Test
    public void testCompararMenor() {
        List<Object> argumentos = Arrays.asList(3, 5);
        boolean resultado = LispBuiltinFunctions.compararMenor(argumentos);
        assertTrue(resultado);
    }

    /**
     * Verifica que la función compararMayor retorne true cuando el primer argumento es mayor que el segundo.
     */
    @Test
    public void testCompararMayor() {
        List<Object> argumentos = Arrays.asList(10, 5);
        boolean resultado = LispBuiltinFunctions.compararMayor(argumentos);
        assertTrue(resultado);
    }

    /**
     * Verifica que la función compararIgual retorne true cuando ambos argumentos son iguales.
     */
    @Test
    public void testCompararIgual() {
        List<Object> argumentos = Arrays.asList(7, 7);
        boolean resultado = LispBuiltinFunctions.compararIgual(argumentos);
        assertTrue(resultado);
    }
}
