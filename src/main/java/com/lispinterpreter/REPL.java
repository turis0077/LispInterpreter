package com.lispinterpreter;

import java.util.Scanner;
import java.util.List;

public class REPL {
    private Evaluator evaluator;

    public REPL() {
        this.evaluator = new Evaluator();
    }

    public void iniciarREPL() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al intérprete de LISP. Escribe 'salir' para terminar.");

        while (true) {
            System.out.print("> ");
            String entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del intérprete...");
                break;
            }

            try {
                Tokenizer tokenizer = new Tokenizer(entrada);
                List<Token> tokens = tokenizer.tokenizar();
                Parser parser = new Parser(tokens);
                Object expresion = parser.parsear();
                Object resultado = evaluator.evaluar(expresion);
                System.out.println(resultado);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        REPL repl = new REPL();
        repl.iniciarREPL();
    }
}
