package com.lispinterpreter;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestREPL {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

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
