package com.lispinterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;

/**
 * Pruebas unitarias para  la clase Parser
 * Se evalúa la conversion de una secuencia de tokens en la estructura anidada correspondiente.
 */
public class TestParser {
    /**
     * Verifica que se parsee correctamente una expresión simple.
     */
    @Test
    public void testParseSimpleExpression() {
        String codigo = "(+ 1 2)";
        Tokenizer tokenizer = new Tokenizer(codigo);
        List<Token> tokens = tokenizer.tokenizar();
        Parser parser = new Parser(tokens);
        Object resultado = parser.parsear();
        assertTrue(resultado instanceof List);
        List<?> listaResultado = (List<?>) resultado;
        assertEquals(3, listaResultado.size());
        assertEquals("+", listaResultado.get(0));
        assertEquals(1, listaResultado.get(1));
        assertEquals(2, listaResultado.get(2));
    }

    /**
     * Verifica que se parsee correctamente una expresión anidada.
     */
    @Test
    public void testParseNestedExpression() {
        String codigo = "(+ 5 (* 2 3))";
        Tokenizer tokenizer = new Tokenizer(codigo);
        List<Token> tokens = tokenizer.tokenizar();
        Parser parser = new Parser(tokens);
        Object resultado = parser.parsear();
        assertTrue(resultado instanceof List);
        List<?> listaResultado = (List<?>) resultado;
        assertEquals("+", listaResultado.get(0));
        assertEquals(5, listaResultado.get(1));
        assertTrue(listaResultado.get(2) instanceof List);
        List<?> sublista = (List<?>) listaResultado.get(2);
        assertEquals("*", sublista.get(0));
        assertEquals(2, sublista.get(1));
        assertEquals(3, sublista.get(2));
    }

    /**
     * Verifica que se parsee correctamente una expresión con "quote".
     */
    @Test
    public void testParseQuoteExpression() {
        String codigo = "'(1 2 3)";
        Tokenizer tokenizer = new Tokenizer(codigo);
        List<Token> tokens = tokenizer.tokenizar();
        Parser parser = new Parser(tokens);
        Object resultado = parser.parsear();
        assertTrue(resultado instanceof List);
        List<?> listaResultado = (List<?>) resultado;
        assertEquals("quote", listaResultado.get(0));
        assertTrue(listaResultado.get(1) instanceof List);
    }
}
