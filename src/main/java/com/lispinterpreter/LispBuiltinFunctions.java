package com.lispinterpreter;

import java.util.List;

public class LispBuiltinFunctions {
    public static int sumar(List<?> argumentos) {
        int resultado = 0;
        for (Object arg : argumentos) {
            if (!(arg instanceof Integer)) {
                throw new RuntimeException("Error: '+' solo opera con números, recibido: " + arg);
            }
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
            if (!(argumentos.get(i) instanceof Integer)) {
                throw new RuntimeException("Error: resta solo opera con números.");
            }
            resultado -= (int) argumentos.get(i);
        }
        return resultado;
    }

    public static int multiplicar(List<?> argumentos) {
        int resultado = 1;
        for (Object arg : argumentos) {
            if (!(arg instanceof Integer)) {
                throw new RuntimeException("Error: '*' solo opera con números.");
            }
            resultado *= (int) arg;
        }
        return resultado;
    }

    public static boolean compararMenor(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: menor requiere exactamente 2 argumentos.");
        }
        if (!(argumentos.get(0) instanceof Integer) || !(argumentos.get(1) instanceof Integer)) {
            throw new RuntimeException("Error: menor solo opera con números.");
        }
        return (int) argumentos.get(0) < (int) argumentos.get(1);
    }

    public static boolean compararMayor(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: mayor requiere exactamente 2 argumentos.");
        }
        if (!(argumentos.get(0) instanceof Integer) || !(argumentos.get(1) instanceof Integer)) {
            throw new RuntimeException("Error: mayor solo opera con números.");
        }
        return (int) argumentos.get(0) > (int) argumentos.get(1);
    }

    public static boolean compararIgual(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: igual? requiere exactamente 2 argumentos.");
        }
        return argumentos.get(0).equals(argumentos.get(1));
    }
}
