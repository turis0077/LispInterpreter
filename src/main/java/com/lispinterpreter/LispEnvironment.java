package com.lispinterpreter;

import java.util.HashMap;
import java.util.Map;

public class LispEnvironment {
    private Map<String, Object> variables;
    private Map<String, LispFunction> funciones;

    public LispEnvironment() {
        this.variables = new HashMap<>();
        this.funciones = new HashMap<>();
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public Map<String, LispFunction> getFunciones() {
        return funciones;
    }

    public Object obtenerVariable(String nombre) {
        if (!variables.containsKey(nombre)) {
            throw new RuntimeException("Error: Variable no definida: " + nombre);
        }
        return variables.get(nombre);
    }

    public void guardarVariable(String nombre, Object valor) {
        variables.put(nombre, valor);
    }

    public LispFunction obtenerFuncion(String nombre) {
        if (!funciones.containsKey(nombre)) {
            throw new RuntimeException("Error: Función no definida: " + nombre);
        }
        return funciones.get(nombre);
    }

    public void guardarFuncion(String nombre, LispFunction funcion) {
        funciones.put(nombre, funcion);
    }

    public boolean existeVariable(String nombre) {
        return variables.containsKey(nombre);
    }

    public boolean existeFuncion(String nombre) {
        return funciones.containsKey(nombre);
    }

}
