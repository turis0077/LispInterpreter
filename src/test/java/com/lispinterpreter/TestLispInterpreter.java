package com.lispinterpreter;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Clase de pruebas unitarias para el intérprete LISP.
 * Se evalúan variables, impresión, operaciones aritméticas, condicionales, definición de funciones y quote.
 */
public class TestLispInterpreter {
    /** Evaluador utilizado para las pruebas. */
    private Evaluator evaluator;
    /** Flujo para capturar la salida estándar. */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /** Flujo original de salida. */
    private final PrintStream originalOut = System.out;

    /**
     * Configura el entorno de pruebas, creando el evaluador y redirigiendo la salida estándar.
     */
    @Before
    public void setUp() {
        evaluator = new Evaluator();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Restaura el flujo de salida estándar original.
     */
    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Metodo auxiliar que tokeniza, parsea y evalúa una cadena de código LISP.
     * @param code Expresión LISP a evaluar.
     * @return Resultado de la evaluación.
     */
    private Object eval(String code) {
        Tokenizer tokenizer = new Tokenizer(code);
        List<Token> tokens = tokenizer.tokenizar();
        Parser parser = new Parser(tokens);
        Object expr = parser.parsear();
        return evaluator.evaluar(expr);
    }

    /**
     * Prueba la asignación de variables y su posterior recuperacion.
     * Evalúa la expresión (setq x 10) y luego la variable x, esperando que ambas retornen 10.
     */
    @Test
    public void testSetqAndVariableRetrieval() {
        // (setq x 10) debe devolver 10 y luego la variable "x" debe evaluarse a 10.
        Object result1 = eval("(setq x 10)");
        assertEquals(10, result1);
        Object result2 = eval("x");
        assertEquals(10, result2);
    }

    /**
     * Prueba la función integrada print.
     * Asigna un valor a la variable x y verifica que se imprima en la salida estóndar,
     * además se confirma que print retorne null.
     */
    @Test
    public void testPrint() {
        // Se asigna "x" y se imprime su valor; se captura la salida por consola.
        eval("(setq x 10)");
        outContent.reset();
        Object result = eval("(print x)");
        String printed = outContent.toString().trim();
        assertTrue(printed.contains("10"));
        // print devuelve null, se verifica que no haya valor.
        assertNull(result);
    }

    /**
     * Prueba la evaluacion de una expresión condicional (if).
     */
    @Test
    public void testArithmetic() {
        // Prueba de operaciones anidadas: (+ 5 (* 2 3)) debe devolver 11.
        Object result = eval("(+ 5 (* 2 3))");
        assertEquals(11, result);
    }

    /**
     * Prueba la definicion de funciones y su ejecucion recursiva.
     * Define la funcion factorial y la evalua.
     */
    @Test
    public void testDefunAndRecursion() {
        Object resultadoDefun = eval("(defun fact (n) (if (equal? n 0) 1 (* n (fact (- n 1)))))");
        assertEquals("Función 'fact' definida.", resultadoDefun);
        Object resultadoFact = eval("(fact 5)");
        assertEquals(120, resultadoFact);
    }

    /**
     * Prueba la forma especial quote.
     * Verifica que retorne una lista sin evalual la expresion.
     */
    @Test
    public void testQuote() {
        Object resultado = eval("'(+ 2 3)");
        assertTrue(resultado instanceof List);
        List<?> listaResultado = (List<?>) resultado;
        assertEquals(3, listaResultado.size());
        assertEquals("+", listaResultado.get(0));
        assertEquals(2, listaResultado.get(1));
        assertEquals(3, listaResultado.get(2));
    }
}