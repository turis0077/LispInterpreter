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
        /**
         * Prueba la ejecución de una función definida que suma dos números.
         * Se define una función "sum" que suma dos números: "(+ a b)".
         * Se espera que al invocar la función con los argumentos 5 y 7, el resultado sea 12.
         */
        List<String> parametros = new ArrayList<>();
        parametros.add("a");
        parametros.add("b");

        List<Object> cuerpo = new ArrayList<>();
        cuerpo.add("+");
        cuerpo.add("a");
        cuerpo.add("b");

        LispFunction funcion = new LispFunction("sum", parametros, cuerpo);
        LispEnvironment entorno = new LispEnvironment();
        List<Object> argumentos = new ArrayList<>();
        argumentos.add(5);
        argumentos.add(7);

        Object resultado = funcion.ejecutar(argumentos, entorno);
        assertEquals(12, resultado);
    }
}
