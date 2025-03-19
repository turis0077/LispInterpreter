package com.lispinterpreter;

import java.util.*;

public class Evaluator {
    private Map<String, Object> variables;

    public Evaluator() {
        this.variables = new HashMap<>();
    }

    public Object evaluar(Object expresion) {
        if (expresion instanceof List) {
            List<?> lista = (List<?>) expresion;
            if (lista.isEmpty()) {
                throw new RuntimeException("Error: Expresión vacía.");
            }

            Object operador = lista.get(0);
            List<?> argumentos = lista.subList(1, lista.size());

            if (operador instanceof String) {
                return evaluarOperacion((String) operador, argumentos);
            } else {
                throw new RuntimeException("Error: Operador no válido.");
            }
        } else if (expresion instanceof String) {
            if (variables.containsKey(expresion)) {
                return variables.get(expresion);
            }
            throw new RuntimeException("Error: Variable no definida: " + expresion);
        } else {
            return expresion;
        }
    }

    private Object evaluarOperacion(String operador, List<?> argumentos) {
        switch (operador) {
            case "+":
                return sumar(argumentos);
            case "*":
                return multiplicar(argumentos);
            case "setq":
                return asignarVariable(argumentos);
            default:
                throw new RuntimeException("Error: Operador no válido: " + operador);
        }
    }

    private int sumar(List<?> argumentos) {
        int resultado = 0;
        for (Object arg : argumentos) {
            resultado += (int) evaluar(arg);
        }
        return resultado;
    }

    private int multiplicar(List<?> argumentos) {
        int resultado = 1;
        for (Object arg : argumentos) {
            resultado *= (int) evaluar(arg);
        }
        return resultado;
    }

    private Object asignarVariable(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: setq requiere exactamente 2 argumentos.");
        }

        String nombre = (String) argumentos.get(0);
        Object valor = evaluar(argumentos.get(1));
        variables.put(nombre, valor);
        return valor;
    }
}
