package com.lispinterpreter;

/**
 * Representa un token en la entrada LISP.
 * Un token tiene un tipo (por ejemplo, número, paréntesis, símbolo, etc.) y un valor.
 */
public class Token {
    /** Tipo del token (por ejemplo, PAREN_OPEN, NUMBER, SYMBOL). */
    private String tipo;
    /** Valor literal del token. */
    private String valor;

    /**
     * Constructor que inicializa el token con su tipo y valor.
     * @param tipo Tipo del token.
     * @param valor Valor del token.
     */
    public Token(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    /**
     * Retorna el tipo del token.
     * @return Tipo del token.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna el valor del token.
     * @return Valor del token.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Devuelve una representación en cadena del token.
     * @return Representación del token.
     */
    @Override
    public String toString() {
        return "Token{" + "tipo='" + tipo + '\'' + ", valor='" + valor + '\'' + '}';
    }
}
