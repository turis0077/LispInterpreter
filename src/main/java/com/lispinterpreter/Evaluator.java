package com.lispinterpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evaluator {
    private Map<String, Object> variables;
    private Map<String, LispFunction> funciones;

    public Evaluator() {
        this.variables = new HashMap<>();
        this.funciones = new HashMap<>();
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
            case "defun":
                return definirFuncion(argumentos);
            default:
                return evaluarFuncion(operador, argumentos);
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

    public void asignarVariable(String nombre, Object valor) {
        variables.put(nombre, valor);
    }

    public void definirFuncion(String nombre, List<String> parametros, Object cuerpo) {
        funciones.put(nombre, new LispFunction(nombre, parametros, cuerpo));
    }

    private Object evaluarFuncion(String nombre, List<?> argumentos) {
        LispFunction funcion = funciones.get(nombre);
        if (funcion == null) {
            throw new RuntimeException("Error: Función no definida: " + nombre);
        }

        List<String> parametros = funcion.getParametros();
        Object cuerpo = funcion.getCuerpo();
        Map<String, Object> contexto = new HashMap<>();

        if (parametros.size() != argumentos.size()) {
            throw new RuntimeException("Error: Número incorrecto de argumentos para la función '" + nombre + "'");
        }

        for (int i = 0; i < parametros.size(); i++) {
            contexto.put(parametros.get(i), evaluar(argumentos.get(i)));
        }

        return evaluarConContexto(cuerpo, contexto);
    }

    private Object evaluarConContexto(Object expresion, Map<String, Object> contexto) {
        if (expresion instanceof List) {
            List<?> lista = (List<?>) expresion;
            if (lista.isEmpty()) {
                throw new RuntimeException("Error: Expresión vacía.");
            }

            Object operador = lista.get(0);
            List<?> argumentos = lista.subList(1, lista.size());

            if (operador instanceof String) {
                return evaluarOperacionConContexto((String) operador, argumentos, contexto);
            } else {
                throw new RuntimeException("Error: Operador no válido.");
            }
        } else if (expresion instanceof String) {
            return contexto.getOrDefault(expresion, variables.get(expresion));
        } else {
            return expresion;
        }
    }

    private Object evaluarOperacionConContexto(String operador, List<?> argumentos, Map<String, Object> contexto) {
        switch (operador) {
            case "+":
                return sumar(argumentos);
            case "*":
                return multiplicar(argumentos);
            default:
                return evaluarFuncionConContexto(operador, argumentos, contexto);
        }
    }

    private Object evaluarFuncionConContexto(String nombre, List<?> argumentos, Map<String, Object> contexto) {
        LispFunction funcion = funciones.get(nombre);
        if (funcion == null) {
            throw new RuntimeException("Error: Función no definida: " + nombre);
        }

        List<String> parametros = funcion.getParametros();
        Object cuerpo = funcion.getCuerpo();
        Map<String, Object> nuevoContexto = new HashMap<>(contexto);

        if (parametros.size() != argumentos.size()) {
            throw new RuntimeException("Error: Número incorrecto de argumentos para la función '" + nombre + "'");
        }

        for (int i = 0; i < parametros.size(); i++) {
            nuevoContexto.put(parametros.get(i), evaluar(argumentos.get(i)));
        }

        return evaluarConContexto(cuerpo, nuevoContexto);
    }
}