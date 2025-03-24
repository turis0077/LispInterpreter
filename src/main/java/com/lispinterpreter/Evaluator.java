package com.lispinterpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Evaluator que se encarga de evaluar expresiones LISP.
 * Soporta formas especiales como if, setq, defun y quote, y realiza llamadas a funciones integradas y definidas.
 */
public class Evaluator {

    /** Entorno en el que se evalúan las expresiones. */
    private LispEnvironment environment;

    /**
     * Constructor que crea un entorno nuevo.
     */
    public Evaluator() {
        this.environment = new LispEnvironment();
    }

    /**
     * Constructor que utiliza un entorno dado.
     * @param environment Entorno para la evaluación.
     */
    public Evaluator(LispEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Evalúa una expresión LISP.
     * Si la expresión es una lista, se procesa de acuerdo al operador (sin evaluar el primer elemento).
     * Si es una cadena, se interpreta como variable.
     * @param expresion Objeto que representa la expresión a evaluar.
     * @return Resultado de la evaluación.
     * @throws RuntimeException si hay errores en la evaluación.
     */
    public Object evaluar(Object expresion) {
        if (expresion instanceof List) {
            List<?> lista = (List<?>) expresion;
            if (lista.isEmpty()) {
                throw new RuntimeException("Error: Expresión vacía.");
            }
            // Se obtiene el operador sin evaluarlo
            Object opObj = lista.get(0);
            if (!(opObj instanceof String)) {
                throw new RuntimeException("Error: Operador no válido: " + opObj);
            }
            String op = (String) opObj;
            // Se manejan las formas especiales sin evaluar el operador
            switch (op) {
                case "if":
                    return evaluarIf(lista);
                case "setq":
                    return evaluarSetq(lista);
                case "defun":
                    return evaluarDefun(lista);
                case "quote":
                    return evaluarQuote(lista);
                default:
                    // Se evalúan únicamente los argumentos (desde la posición 1)
                    List<Object> argsEvaluados = new ArrayList<>();
                    for (int i = 1; i < lista.size(); i++) {
                        argsEvaluados.add(evaluar(lista.get(i)));
                    }
                    return aplicarOperacion(op, argsEvaluados);
            }
        } else if (expresion instanceof String) {
            String s = (String) expresion;
            if (environment.existeVariable(s)) {
                return environment.obtenerVariable(s);
            }
            throw new RuntimeException("Error: Variable no definida: " + s);
        } else {
            return expresion;
        }
    }

    /**
     * Evalúa la forma especial if.
     * La estructura es: (if condición rama-verdadera rama-falsa)
     * @param expr Lista que representa la expresión if.
     * @return Resultado de evaluar la rama correspondiente.
     */
    private Object evaluarIf(List<?> expr) {
        if (expr.size() != 4) {
            throw new RuntimeException("Error: if requiere 3 argumentos.");
        }
        Object condicion = evaluar(expr.get(1));
        if (!(condicion instanceof Boolean)) {
            throw new RuntimeException("Error: La condición en if debe evaluarse a booleano.");
        }
        if ((Boolean) condicion) {
            return evaluar(expr.get(2));
        } else {
            return evaluar(expr.get(3));
        }
    }

    /**
     * Evalúa la forma especial setq para asignar variables.
     * La estructura es: (setq nombre valor)
     * @param expr Lista que representa la expresión setq.
     * @return Valor asignado.
     */
    private Object evaluarSetq(List<?> expr) {
        if (expr.size() != 3) {
            throw new RuntimeException("Error: setq requiere exactamente 2 argumentos.");
        }
        String nombre = (String) expr.get(1);
        Object valor = evaluar(expr.get(2));
        environment.guardarVariable(nombre, valor);
        return valor;
    }

    /**
     * Evalúa la forma especial defun para definir funciones.
     * La estructura es: (defun nombre (parámetros) cuerpo)
     * @param expr Lista que representa la expresión defun.
     * @return Mensaje confirmando la definición de la función.
     */
    private Object evaluarDefun(List<?> expr) {
        if (expr.size() != 4) {
            throw new RuntimeException("Error: defun requiere exactamente 3 argumentos.");
        }
        String nombre = (String) expr.get(1);
        Object paramsObj = expr.get(2);
        if (!(paramsObj instanceof List)) {
            throw new RuntimeException("Error: Los parámetros deben estar en una lista.");
        }
        List<?> paramsRaw = (List<?>) paramsObj;
        List<String> parametros = new ArrayList<>();
        for (Object p : paramsRaw) {
            if (!(p instanceof String)) {
                throw new RuntimeException("Error: Cada parámetro debe ser un símbolo.");
            }
            parametros.add((String) p);
        }
        Object cuerpo = expr.get(3);
        LispFunction funcion = new LispFunction(nombre, parametros, cuerpo);
        environment.guardarFuncion(nombre, funcion);
        return "Función '" + nombre + "' definida.";
    }

    /**
     * Evalúa la forma especial quote que evita la evaluación de la expresión.
     * La estructura es: (quote expresión)
     * @param expr Lista que representa la expresión quote.
     * @return La expresión sin evaluar.
     */
    private Object evaluarQuote(List<?> expr) {
        if (expr.size() != 2) {
            throw new RuntimeException("Error: quote requiere exactamente 1 argumento.");
        }
        return expr.get(1);
    }

    /**
     * Aplica una operación (ya sea función integrada o definida) a los argumentos evaluados.
     * @param operador Nombre del operador o función.
     * @param argumentos Lista de argumentos ya evaluados.
     * @return Resultado de la operación.
     * @throws RuntimeException si la función no está definida.
     */
    private Object aplicarOperacion(String operador, List<Object> argumentos) {
        switch (operador) {
            case "+":
                return LispBuiltinFunctions.sumar(argumentos);
            case "-":
                return LispBuiltinFunctions.restar(argumentos);
            case "*":
                return LispBuiltinFunctions.multiplicar(argumentos);
            case "<":
                return LispBuiltinFunctions.compararMenor(argumentos);
            case ">":
                return LispBuiltinFunctions.compararMayor(argumentos);
            case "equal?":
                return LispBuiltinFunctions.compararIgual(argumentos);
            case "print":
                return evaluarPrint(argumentos);
            default:
                if (environment.existeFuncion(operador)) {
                    LispFunction funcion = environment.obtenerFuncion(operador);
                    return funcion.ejecutar(argumentos, environment);
                }
                throw new RuntimeException("Error: Función no definida: " + operador);
        }
    }

    /**
     * Ejecuta la función integrada print, que muestra los argumentos en la salida estándar.
     * @param argumentos Lista de argumentos a imprimir.
     * @return null (print no retorna un valor significativo).
     */
    private Object evaluarPrint(List<Object> argumentos) {
        for (Object arg : argumentos) {
            System.out.print(arg + " ");
        }
        System.out.println();
        return null;
    }

    /**
     * Define una funcion en el entorno, que se define a partir de los argumentos: nombre de funcion (String),
     * lista de parametros (List, String), cuerpo de la función
     * Guarda la funcion en el entorno y retorna un mensaje confirmando su definición.
     * @param argumentos Lista de argumentos que debe contener exactamente tres elementos.
     * @return Un mensaje indicando que la función ha sido definida.
     * @throws RuntimeException si el número de argumentos no es exactamente 3.
     */
    private Object definirFuncion(List<?> argumentos) {
        if (argumentos.size() != 3) {
            throw new RuntimeException("Error: defun requiere exactamente 3 argumentos.");
        }

        String nombre = (String) argumentos.get(0);
        List<String> parametros = (List<String>) argumentos.get(1);
        Object cuerpo = argumentos.get(2);

        LispFunction funcion = new LispFunction(nombre, parametros, cuerpo);
        environment.guardarFuncion(nombre, funcion);
        return "Función '" + nombre + "' definida.";
    }

    /**
     * Ejecuta la función integrada print, que muestra los argumentos en la salida estándar.
     * Se evalúa cada argumento y se imprime separado por un espacio, finalizando con un salto de línea.
     * @param argumentos Lista de argumentos a imprimir.
     * @return null (print no retorna un valor significativo).
     */
    private Object imprimir(List<?> argumentos) {
        for (Object arg : argumentos) {
            System.out.print(evaluar(arg) + " ");
        }
        System.out.println();
        return null;
    }

    /**
     * Evalúa una expresión condicional (if).
     * La estructura debe ser: (if condición rama-verdadera rama-falsa). Se evalúa la condición;
     * si es verdadera se retorna la evaluación de la rama-verdadera,
     * de lo contrario se retorna la evaluación de la rama-falsa.
     * @param argumentos Lista que contiene exactamente tres elementos: la condición, la rama-verdadera y la rama-falsa.
     * @return El resultado de la evaluación de la rama correspondiente.
     * @throws RuntimeException si el número de argumentos no es 3.
     */
    private Object evaluarCondicional(List<?> argumentos) {
        if (argumentos.size() != 3) {
            throw new RuntimeException("Error: if requiere exactamente 3 argumentos.");
        }
        boolean condicion = (boolean) argumentos.get(0);
        return condicion ? argumentos.get(1) : argumentos.get(2);
    }

    /**
     * Evalúa la invocación de una función definida en el entorno.
     * Se verifica que la función con el nombre especificado exista en el entorno y, en caso afirmativo, se ejecuta pasándole la lista de argumentos.
     * @param nombre Nombre de la función a invocar.
     * @param argumentos Lista de argumentos para la función.
     * @return El resultado de ejecutar la función.
     * @throws RuntimeException si la función no está definida en el entorno.
     */
    private Object evaluarFuncion(String nombre, List<?> argumentos) {
        if (!environment.existeFuncion(nombre)) {
            throw new RuntimeException("Error: Función no definida: " + nombre);
        }
        LispFunction funcion = environment.obtenerFuncion(nombre);
        return funcion.ejecutar(argumentos, environment);
    }
}
