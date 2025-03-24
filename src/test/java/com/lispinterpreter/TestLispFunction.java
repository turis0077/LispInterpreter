package com.lispinterpreter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Pruebas unitarias para la clase LispFunction.
 * Se verifica la correcta ejecución de funciones definidas por el usuario.
 */
public class TestLispFunction {
    @Test
    public void testLispFunctionSum() {
        /** Se define una función que suma dos números: (+ a b) */
        List<String> parametros = new ArrayList<>();
        parametros.add("a");
        parametros.add("b");

        List<Object> cuerpo = new ArrayList<>();
        cuerpo.add("+");
        cuerpo.add("a");
        cuerpo.add("b");

        LispFunction funcion = new LispFunction("sum", parametros, cuerpo);

        /** Se crea un entorno (vacío, en este caso) para la ejecución de la función. */
        LispEnvironment entorno = new LispEnvironment();

        /** Se preparan los argumentos: 5 y 7. */
        List<Object> argumentos = new ArrayList<>();
        argumentos.add(5);
        argumentos.add(7);

        Object resultado = funcion.ejecutar(argumentos, entorno);
        assertEquals(12, resultado);
    }
}
