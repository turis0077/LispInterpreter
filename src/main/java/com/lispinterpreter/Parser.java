package com.lispinterpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Parser que transforma una lista de tokens en una estructura de datos (listas anidadas)
 * que representa la expresión LISP.
 */
public class Parser {
    /** Lista de tokens a procesar. */
    private List<Token> tokens;
    /** Posición actual de análisis en la lista de tokens. */
    private int posicion;

    /**
     * Constructor que recibe la lista de tokens.
     * @param tokens Lista de tokens generada por el Tokenizer.
     */
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0;
    }

    /**
     * Método principal para parsear la expresión.
     * Convierte la secuencia de tokens en una estructura anidada de listas y valores.
     * @return La expresión parseada.
     * @throws RuntimeException si la expresión está vacía o falta un paréntesis de cierre.
     */
    public Object parsear() {
        if (posicion >= tokens.size()) {
            throw new RuntimeException("Error: Expresión inesperadamente vacía.");
        }

        Token token = tokens.get(posicion);
        posicion++;

        if (token.getTipo().equals("QUOTE")) {
            Object quotedExpr = parsear();
            List<Object> listQuote = new ArrayList<>();
            listQuote.add("quote");
            listQuote.add(quotedExpr);
            return listQuote;
        } else if (token.getTipo().equals("PAREN_OPEN")) {
            List<Object> expresion = new ArrayList<>();
            while (posicion < tokens.size() && !tokens.get(posicion).getTipo().equals("PAREN_CLOSE")) {
                expresion.add(parsear());
            }
            if (posicion >= tokens.size()) {
                throw new RuntimeException("Error: Falta paréntesis de cierre.");
            }
            posicion++;
            return expresion;
        } else if (token.getTipo().equals("NUMBER")) {
            return Integer.parseInt(token.getValor());
        } else {
            return token.getValor();
        }
    }
}
