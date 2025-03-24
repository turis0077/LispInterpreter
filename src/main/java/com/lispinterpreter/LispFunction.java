package com.lispinterpreter;

import java.util.List;
import java.util.Map;

/**
 * Representa una función definida en LISP.
 * Contiene el nombre de la función, la lista de parámetros y el cuerpo (expresión a evaluar).
 */
public class LispFunction {

    /** Nombre de la función. */
    private String nombre;
    /** Lista de parámetros de la función. */
    private List<String> parametros;
    /** Cuerpo de la función (expresión LISP que se evaluará al llamar la función). */
    private Object cuerpo;

    /**
     * Constructor que inicializa la función con su nombre, parámetros y cuerpo.
     * @param nombre Nombre de la función.
     * @param parametros Lista de nombres de parámetros.
     * @param cuerpo Expresión que representa el cuerpo de la función.
     */
    public LispFunction(String nombre, List<String> parametros, Object cuerpo) {
        this.nombre = nombre;
        this.parametros = parametros;
        this.cuerpo = cuerpo;
    }

    /**
     * Retorna el nombre de la función.
     * @return Nombre de la función.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna la lista de parámetros de la función.
     * @return Lista de parámetros.
     */
    public List<String> getParametros() {
        return parametros;
    }

    /**
     * Retorna el cuerpo de la función.
     * @return Cuerpo de la función.
     */
    public Object getCuerpo() {
        return cuerpo;
    }

    /**
     * Ejecuta la función definida con los argumentos proporcionados.
     * Se crea un nuevo entorno que copia las variables y funciones del entorno actual,
     * se asignan los argumentos a los parámetros y se evalúa el cuerpo de la función.
     *
     * @param argumentos Lista de argumentos para la función.
     * @param entorno Entorno actual en el que se ejecuta la función.
     * @return Resultado de la evaluación del cuerpo.
     * @throws RuntimeException si el número de argumentos no coincide con el número de parámetros.
     */
    public Object ejecutar(List<?> argumentos, LispEnvironment entorno) {
        if (parametros.size() != argumentos.size()) {
            throw new RuntimeException("Error: Número incorrecto de argumentos para la función '" + nombre + "'");
        }

        LispEnvironment nuevoEntorno = new LispEnvironment();

        for (Map.Entry<String, Object> entry : entorno.getVariables().entrySet()) {
            nuevoEntorno.guardarVariable(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, LispFunction> entry : entorno.getFunciones().entrySet()) {
            nuevoEntorno.guardarFuncion(entry.getKey(), entry.getValue());
        }

        for (int i = 0; i < parametros.size(); i++) {
            nuevoEntorno.guardarVariable(parametros.get(i), argumentos.get(i));
        }

        Evaluator evaluador = new Evaluator(nuevoEntorno);
        return evaluador.evaluar(cuerpo);
    }
}
