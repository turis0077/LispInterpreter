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
        List<Token> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\(|\\)|[+\\-*/<>=]|\\w+|\\d+");
        Matcher matcher = pattern.matcher(entrada);

        while (matcher.find()) {
            String valor = matcher.group();

            if (valor.equals("(")) {
                tokens.add(new Token("PAREN_OPEN", valor));
            } else if (valor.equals(")")) {
                tokens.add(new Token("PAREN_CLOSE", valor));
            } else if (valor.matches("\\d+")) {
                tokens.add(new Token("NUMBER", valor));
            } else {
                tokens.add(new Token("SYMBOL", valor));
            }
        }

        return tokens;
    }
}
