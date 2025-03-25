package com.lispinterpreter;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase Tokenizer.
 * Se verifica que la tokenización de una cadena de entrada LISP genere la lista de tokens esperada.
 */
public class TestTokenizer {

    /**
     * Verifica la tokenización de una expersión simple.
     */
    @Test
    public void testTokenizeSimpleExpression() {
        String codigo = "(+ 1 2)";
        Tokenizer tokenizer = new Tokenizer(codigo);
        List<Token> tokens = tokenizer.tokenizar();
        assertEquals(5, tokens.size());
        assertEquals("PAREN_OPEN", tokens.get(0).getTipo());
        assertEquals("(", tokens.get(0).getValor());
        assertEquals("SYMBOL", tokens.get(1).getTipo());
        assertEquals("+", tokens.get(1).getValor());
        assertEquals("NUMBER", tokens.get(2).getTipo());
        assertEquals("1", tokens.get(2).getValor());
        assertEquals("NUMBER", tokens.get(3).getTipo());
        assertEquals("2", tokens.get(3).getValor());
        assertEquals("PAREN_CLOSE", tokens.get(4).getTipo());
        assertEquals(")", tokens.get(4).getValor());
    }

    /**
     * Verifica la tokenización de una expresión que incluye la notación de quote.
     */
    @Test
    public void testTokenizeWithQuote() {
        String codigo = "'(a b c)";
        Tokenizer tokenizer = new Tokenizer(codigo);
        List<Token> tokens = tokenizer.tokenizar();
        assertEquals(6, tokens.size());
        assertEquals("QUOTE", tokens.get(0).getTipo());
        assertEquals("'", tokens.get(0).getValor());
        assertEquals("PAREN_OPEN", tokens.get(1).getTipo());
        assertEquals("(", tokens.get(1).getValor());
        assertEquals("SYMBOL", tokens.get(2).getTipo());
        assertEquals("a", tokens.get(2).getValor());
        assertEquals("SYMBOL", tokens.get(3).getTipo());
        assertEquals("b", tokens.get(3).getValor());
        assertEquals("SYMBOL", tokens.get(4).getTipo());
        assertEquals("c", tokens.get(4).getValor());
        assertEquals("PAREN_CLOSE", tokens.get(5).getTipo());
        assertEquals(")", tokens.get(5).getValor());
    }
}
