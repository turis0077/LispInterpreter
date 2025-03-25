package com.lispinterpreter;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase Token.
 * Se verifica la correcta creación de tokens y el funcionamiento de sus métodos.
 */
public class TestToken {
    /**
     * Verifica que al crear un token se asignen correctamente el tipo y el valor.
     */
    @Test
    public void testTokenCreation() {
        Token token = new Token("NUMBER", "123");
        assertEquals("NUMBER", token.getTipo());
        assertEquals("123", token.getValor());
    }

    /**
     * Verifica que el metodo toString devuelva una representación adecuada del token.
     */
    @Test
    public void testTokenToString() {
        Token token = new Token("SYMBOL", "x");
        String tokenStr = token.toString();
        assertTrue(tokenStr.contains("SYMBOL"));
        assertTrue(tokenStr.contains("x"));
    }
}
