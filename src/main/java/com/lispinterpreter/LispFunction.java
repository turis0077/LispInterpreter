package com.lispinterpreter;

import java.util.List;
import java.util.Map;

public class LispFunction {
    private String nombre;
    private List<String> parametros;
    private Object cuerpo;

    public LispFunction(String nombre, List<String> parametros, Object cuerpo) {
        this.nombre = nombre;
        this.parametros = parametros;
        this.cuerpo = cuerpo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getParametros() {
        return parametros;
    }

    public Object getCuerpo() {
        return cuerpo;
    }

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
