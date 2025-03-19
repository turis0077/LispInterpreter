package com.lispinterpreter;

import java.util.List;

public class LispBuiltinFunctions {
    public static int sumar(List<?> argumentos) {
        int resultado = 0;
        for (Object arg : argumentos) {
            resultado += (int) arg;
        }
        return resultado;
    }

    public static int restar(List<?> argumentos) {
        if (argumentos.isEmpty()) {
            throw new RuntimeException("Error: resta requiere al menos un argumento.");
        }
        int resultado = (int) argumentos.get(0);
        for (int i = 1; i < argumentos.size(); i++) {
            resultado -= (int) argumentos.get(i);
        }
        return resultado;
    }

    public static int multiplicar(List<?> argumentos) {
        int resultado = 1;
        for (Object arg : argumentos) {
            resultado *= (int) arg;
        }
        return resultado;
    }

    public static boolean compararMenor(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: menor requiere exactamente 2 argumentos.");
        }
        return (int) argumentos.get(0) < (int) argumentos.get(1);
    }

    public static boolean compararMayor(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: mayor requiere exactamente 2 argumentos.");
        }
        return (int) argumentos.get(0) > (int) argumentos.get(1);
    }
}
