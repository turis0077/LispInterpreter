package com.lispinterpreter;

import java.util.List;
import java.util.stream.Collectors;

public class Evaluator {
    private LispEnvironment environment;

    public Evaluator() {
        this.environment = new LispEnvironment();
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
            return environment.obtenerVariable((String) expresion);
        } else {
            return expresion;
        }
    }

    private Object evaluarOperacion(String operador, List<?> argumentos) {
        List<Object> valoresEvaluados = argumentos.stream()
                .map(this::evaluar)
                .collect(Collectors.toList());
        
        switch (operador) {
            case "+":
                return LispBuiltinFunctions.sumar(argumentos);
            case "-":
                return LispBuiltinFunctions.restar(argumentos);
            case "*":
                return LispBuiltinFunctions.multiplicar(argumentos);
            case "setq":
                return asignarVariable(argumentos);
            case "defun":
                return definirFuncion(argumentos);
            case "print":
                return imprimir(argumentos);
            case "<":
                return LispBuiltinFunctions.compararMenor(argumentos);
            case ">":
                return LispBuiltinFunctions.compararMayor(argumentos);
            case "equal?":
                return LispBuiltinFunctions.compararIgual(argumentos);
            default:
                return evaluarFuncion(operador, argumentos);
        }
    }

    private Object asignarVariable(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: setq requiere exactamente 2 argumentos.");
        }

        String nombre = (String) argumentos.get(0);
        Object valor = evaluar(argumentos.get(1));
        environment.guardarVariable(nombre, valor);
        return valor;
    }

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

    private Object imprimir(List<?> argumentos) {
        for (Object arg : argumentos) {
            System.out.print(evaluar(arg) + " ");
        }
        System.out.println();
        return null;
    }

    private Object evaluarCondicional(List<?> argumentos) {
        if (argumentos.size() != 3) {
            throw new RuntimeException("Error: if requiere exactamente 3 argumentos.");
        }
        boolean condicion = (boolean) argumentos.get(0);
        return condicion ? argumentos.get(1) : argumentos.get(2);
    }

    private Object evaluarFuncion(String nombre, List<?> argumentos) {
        LispFunction funcion = environment.obtenerFuncion(nombre);
        if (funcion == null) {
            throw new RuntimeException("Error: Función no definida: " + nombre);
        }
        return funcion.ejecutar(argumentos, environment);
    }
}
