package com.lispinterpreter;

import java.util.List;

/**
 * Clase que contiene las funciones integradas (built-in) del intérprete LISP.
 * Incluye operaciones aritméticas y predicados.
 */
public class LispBuiltinFunctions {

    /**
     * Suma una lista de números.
     * @param argumentos Lista de argumentos que deben ser números.
     * @return La suma de todos los números.
     * @throws RuntimeException si algún argumento no es un número.
     */
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

    /**
     * Resta una lista de números.
     * @param argumentos Lista de argumentos donde el primero es el minuendo.
     * @return Resultado de la resta secuencial.
     * @throws RuntimeException si la lista está vacía o algún argumento no es un número.
     */
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

    /**
     * Multiplica una lista de números.
     * @param argumentos Lista de argumentos que deben ser números.
     * @return Producto de todos los números.
     * @throws RuntimeException si algún argumento no es un número.
     */
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

    /**
     * Compara si el primer número es menor que el segundo.
     * @param argumentos Lista de dos números.
     * @return true si el primer número es menor que el segundo.
     * @throws RuntimeException si la lista no contiene exactamente dos números.
     */
    public static boolean compararMenor(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: menor requiere exactamente 2 argumentos.");
        }
        if (!(argumentos.get(0) instanceof Integer) || !(argumentos.get(1) instanceof Integer)) {
            throw new RuntimeException("Error: menor solo opera con números.");
        }
        return (int) argumentos.get(0) < (int) argumentos.get(1);
    }

    /**
     * Compara si el primer número es mayor que el segundo.
     * @param argumentos Lista de dos números.
     * @return true si el primer número es mayor que el segundo.
     * @throws RuntimeException si la lista no contiene exactamente dos números.
     */
    public static boolean compararMayor(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: mayor requiere exactamente 2 argumentos.");
        }
        if (!(argumentos.get(0) instanceof Integer) || !(argumentos.get(1) instanceof Integer)) {
            throw new RuntimeException("Error: mayor solo opera con números.");
        }
        return (int) argumentos.get(0) > (int) argumentos.get(1);
    }

    /**
     * Verifica si dos argumentos son iguales.
     * @param argumentos Lista de dos argumentos.
     * @return true si ambos argumentos son iguales.
     * @throws RuntimeException si la lista no contiene exactamente dos argumentos.
     */
    public static boolean compararIgual(List<?> argumentos) {
        if (argumentos.size() != 2) {
            throw new RuntimeException("Error: igual? requiere exactamente 2 argumentos.");
        }
        return argumentos.get(0).equals(argumentos.get(1));
    }
}
