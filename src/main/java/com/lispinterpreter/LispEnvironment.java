package com.lispinterpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa el entorno de ejecución para el intérprete LISP.
 * Almacena variables y funciones definidas.
 */
public class LispEnvironment {

    /** Mapa que almacena las variables definidas. */
    private Map<String, Object> variables;
    /** Mapa que almacena las funciones definidas. */
    private Map<String, LispFunction> funciones;

    /**
     * Constructor que inicializa los mapas para variables y funciones.
     */
    public LispEnvironment() {
        this.variables = new HashMap<>();
        this.funciones = new HashMap<>();
    }

    /**
     * Retorna el mapa de variables.
     * @return Mapa de variables.
     */
    public Map<String, Object> getVariables() {
        return variables;
    }

    /**
     * Retorna el mapa de funciones.
     * @return Mapa de funciones.
     */
    public Map<String, LispFunction> getFunciones() {
        return funciones;
    }

    /**
     * Obtiene el valor de una variable definida en el entorno.
     * @param nombre Nombre de la variable.
     * @return Valor de la variable.
     * @throws RuntimeException si la variable no está definida.
     */
    public Object obtenerVariable(String nombre) {
        if (!variables.containsKey(nombre)) {
            throw new RuntimeException("Error: Variable no definida: " + nombre);
        }
        return variables.get(nombre);
    }

    /**
     * Guarda o actualiza el valor de una variable en el entorno.
     * @param nombre Nombre de la variable.
     * @param valor Valor a asignar.
     */
    public void guardarVariable(String nombre, Object valor) {
        variables.put(nombre, valor);
    }

    /**
     * Obtiene una función definida en el entorno.
     * @param nombre Nombre de la función.
     * @return Objeto LispFunction correspondiente.
     * @throws RuntimeException si la función no está definida.
     */
    public LispFunction obtenerFuncion(String nombre) {
        if (!funciones.containsKey(nombre)) {
            throw new RuntimeException("Error: Función no definida: " + nombre);
        }
        return funciones.get(nombre);
    }

    /**
     * Guarda una función en el entorno.
     * @param nombre Nombre de la función.
     * @param funcion Objeto LispFunction a almacenar.
     */
    public void guardarFuncion(String nombre, LispFunction funcion) {
        funciones.put(nombre, funcion);
    }

    /**
     * Verifica si una variable existe en el entorno.
     * @param nombre Nombre de la variable.
     * @return true si la variable existe, false de lo contrario.
     */
    public boolean existeVariable(String nombre) {
        return variables.containsKey(nombre);
    }

    /**
     * Verifica si una función existe en el entorno.
     * @param nombre Nombre de la función.
     * @return true si la función existe, false de lo contrario.
     */
    public boolean existeFuncion(String nombre) {
        return funciones.containsKey(nombre);
    }
}
