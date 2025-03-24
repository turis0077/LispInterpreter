package com.lispinterpreter;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class TestLispInterpreter {
    private Evaluator evaluator;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        evaluator = new Evaluator();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    // Método auxiliar para evaluar una cadena de código Lisp.
    private Object eval(String code) {
        Tokenizer tokenizer = new Tokenizer(code);
        List<Token> tokens = tokenizer.tokenizar();
        Parser parser = new Parser(tokens);
        Object expr = parser.parsear();
        return evaluator.evaluar(expr);
    }

    @Test
    public void testSetqAndVariableRetrieval() {
        // (setq x 10) debe devolver 10 y luego la variable "x" debe evaluarse a 10.
        Object result1 = eval("(setq x 10)");
        assertEquals(10, result1);
        Object result2 = eval("x");
        assertEquals(10, result2);
    }

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

    @Test
    public void testArithmetic() {
        // Prueba de operaciones anidadas: (+ 5 (* 2 3)) debe devolver 11.
        Object result = eval("(+ 5 (* 2 3))");
        assertEquals(11, result);
    }
}