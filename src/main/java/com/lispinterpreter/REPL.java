package com.lispinterpreter;

import java.util.List;
import java.util.Scanner;

/**
 * Clase REPL (Read, Evaluate, Print, Loop) que implementa el intérprete interactivo de LISP.
 * Permite al usuario ingresar expresiones en LISP, evaluarlas y mostrar los resultados.
 */
public class REPL {
    /** Evaluador que se usa para procesar expresiones LISP. */
    private Evaluator evaluator;

    /**
     * Constructor que inicializa el evaluador.
     */
    public REPL() {
        this.evaluator = new Evaluator();
    }

    /**
     * Inicia el ciclo interactivo del intérprete.
     * Lee la entrada del usuario, la tokeniza, parsea y evalúa la expresión.
     * Finaliza cuando el usuario ingresa "salir".
     */
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
                if (resultado != null) {
                    System.out.println(resultado);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Método principal que inicia el intérprete LISP.
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        REPL repl = new REPL();
        repl.iniciarREPL();
    }
}
