package com.lispinterpreter;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase REPL.
 * Se simula la entrada interactiva para verificar el mensaje de salida.
 */
public class TestREPL {
    /** Flujo para capturar la salida estándar. */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /** Flujo original de salida. */
    private final PrintStream originalOut = System.out;
    /** Flujo original de entrada. */
    private final java.io.InputStream originalIn = System.in;

    /**
     * Configura el entorno de pruebas redirigiendo la salida estándar.
     */
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Restaura los flujos de entrada y salida originales.
     */
    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testREPLExit() {
        // Simulamos que el usuario escribe "salir" para terminar la sesión.
        String simulatedInput = "salir\n";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        REPL repl = new REPL();
        repl.iniciarREPL();

        String output = outContent.toString();
        // Verifica que se muestre el mensaje de salida "Saliendo del intérprete..."
        assertTrue(output.contains("Saliendo del intérprete..."));
    }
}
