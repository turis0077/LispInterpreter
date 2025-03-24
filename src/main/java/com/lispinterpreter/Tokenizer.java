package com.lispinterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase Tokenizer que se encarga de dividir un String de entrada LISP en tokens.
 */
public class Tokenizer {

    /** String de entrada a tokenizar. */
    private String entrada;

    /**
     * Constructor que recibe la cadena de entrada.
     * @param entrada La expresión LISP a tokenizar.
     */
    public Tokenizer(String entrada) {
        this.entrada = entrada;
    }

    /**
     * Tokeniza la cadena de entrada.
     * Se reconocen paréntesis, comillas, operadores, números y símbolos (incluyendo el carácter '?').
     * @return Lista de tokens generados a partir de la entrada.
     */
    public List<Token> tokenizar() {
        // La expresión regular reconoce paréntesis, comillas, operadores y símbolos que pueden contener '?'
        List<Token> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\(|\\)|'|[+\\-*/<>=]|[a-zA-Z_][a-zA-Z0-9_?]*|\\d+");
        Matcher matcher = pattern.matcher(entrada);

        while (matcher.find()) {
            String valor = matcher.group();

            if (valor.equals("(")) {
                tokens.add(new Token("PAREN_OPEN", valor));
            } else if (valor.equals(")")) {
                tokens.add(new Token("PAREN_CLOSE", valor));
            } else if (valor.equals("'")) {
                tokens.add(new Token("QUOTE", valor));
            } else if (valor.matches("\\d+")) {
                tokens.add(new Token("NUMBER", valor));
            } else {
                tokens.add(new Token("SYMBOL", valor));
            }
        }

        return tokens;
    }
}
