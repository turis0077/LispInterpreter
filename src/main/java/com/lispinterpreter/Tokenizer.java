package com.lispinterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private String entrada;

    public Tokenizer(String entrada) {
        this.entrada = entrada;
    }

    public List<Token> tokenizar() {
        Pattern pattern = Pattern.compile("\\(|\\)|'|[+\\-*/<>=]|[a-zA-Z_][a-zA-Z0-9_?]*|\\d+");
        Matcher matcher = pattern.matcher(entrada);
        List<Token> tokens = new ArrayList<>();

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
