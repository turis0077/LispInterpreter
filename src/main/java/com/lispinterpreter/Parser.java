package com.lispinterpreter;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int posicion;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0;
    }

    public Object parsear() {
        if (posicion >= tokens.size()) {
            throw new RuntimeException("Error: Expresión inesperadamente vacía.");
        }

        Token token = tokens.get(posicion);
        posicion++;

        if (token.getTipo().equals("QUOTE")) {
            // Convierte 'exp en (quote exp)
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
            posicion++; // Saltar el paréntesis de cierre
            return expresion;
        } else if (token.getTipo().equals("NUMBER")) {
            return Integer.parseInt(token.getValor());
        } else {
            return token.getValor(); // Símbolo o nombre de variable/función
        }
    }
}
