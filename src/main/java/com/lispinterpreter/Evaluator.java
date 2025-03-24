package com.lispinterpreter;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {
    private LispEnvironment environment;

    public Evaluator() {
        this.environment = new LispEnvironment();
    }

    public Evaluator(LispEnvironment environment) {
        this.environment = environment;
    }

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

    private Object evaluarSetq(List<?> expr) {
        if (expr.size() != 3) {
            throw new RuntimeException("Error: setq requiere exactamente 2 argumentos.");
        }
        String nombre = (String) expr.get(1);
        Object valor = evaluar(expr.get(2));
        environment.guardarVariable(nombre, valor);
        return valor;
    }

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

    private Object evaluarQuote(List<?> expr) {
        if (expr.size() != 2) {
            throw new RuntimeException("Error: quote requiere exactamente 1 argumento.");
        }
        return expr.get(1);
    }

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

    private Object evaluarPrint(List<Object> argumentos) {
        for (Object arg : argumentos) {
            System.out.print(arg + " ");
        }
        System.out.println();
        return null;
    }
}
