package com.lispinterpreter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

class TestLispInterpreter {

    @Test
    void probarToken() {
        Token token = new Token("SYMBOL", "+");
        assertEquals("SYMBOL", token.getTipo());
        assertEquals("+", token.getValor());

        Token numero = new Token("NUMBER", "5");
        assertEquals("NUMBER", numero.getTipo());
        assertEquals("5", numero.getValor());
    }

    @Test
    void probarTokenizer() {
        Tokenizer tokenizer = new Tokenizer("(+ 5 (* 2 3))");
        List<Token> tokens = tokenizer.tokenizar();

        assertEquals(9, tokens.size()); // Se espera 9 tokens
        assertEquals("PAREN_OPEN", tokens.get(0).getTipo());
        assertEquals("+", tokens.get(1).getValor());
        assertEquals("NUMBER", tokens.get(2).getTipo());
        assertEquals("5", tokens.get(2).getValor());
        assertEquals("PAREN_CLOSE", tokens.get(8).getTipo());
    }

    @Test
    void probarParser() {
        Tokenizer tokenizer = new Tokenizer("(+ 5 (* 2 3))");
        List<Token> tokens = tokenizer.tokenizar();
        Parser parser = new Parser(tokens);
        Object resultado = parser.parsear();

        // Verifica que el parser devuelve una lista con la estructura correcta
        assertTrue(resultado instanceof List);
        List<?> lista = (List<?>) resultado;

        assertEquals("+", lista.get(0));
        assertEquals(5, lista.get(1));
        assertTrue(lista.get(2) instanceof List);
        List<?> sublista = (List<?>) lista.get(2);
        assertEquals("*", sublista.get(0));
        assertEquals(2, sublista.get(1));
        assertEquals(3, sublista.get(2));
    }

    @Test
    void probarEvaluator() {
        Evaluator evaluator = new Evaluator();

        // Prueba suma simple
        Object resultadoSuma = evaluator.evaluar(List.of("+", 2, 3));
        assertEquals(5, resultadoSuma);

        // Prueba multiplicación
        Object resultadoMultiplicacion = evaluator.evaluar(List.of("*", 4, 2));
        assertEquals(8, resultadoMultiplicacion);

        // Prueba de setq (asignación de variable)
        Object resultadoSetq = evaluator.evaluar(List.of("setq", "x", 10));
        assertEquals(10, resultadoSetq);
        assertEquals(10, evaluator.evaluar("x"));

        // Prueba de suma con variable
        Object resultadoSumaVariable = evaluator.evaluar(List.of("+", "x", 5));
        assertEquals(15, resultadoSumaVariable);
    }

    @Test
    void probarREPL() {
        REPL repl = new REPL();
        assertNotNull(repl); // Verifica que la instancia de REPL no sea null
    }
}
