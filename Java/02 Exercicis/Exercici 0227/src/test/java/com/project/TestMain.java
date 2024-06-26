package com.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.github.stefanbirkner.systemlambda.SystemLambda;

import java.io.ByteArrayInputStream;
import java.time.Year;
import java.util.Locale;

public class TestMain {

    @Test
    public void testValidaTargeta() throws Exception {
        Locale defaultLocale = Locale.getDefault(); // Guarda la configuració regional per defecte
        Locale.setDefault(Locale.US); // Estableix la configuració regional a US

        try {
            int anyActual = Year.now().getValue();
            String[] inputs = {
                "2020\n",
                "2021\n",
                "2053\n",
                "2065\n",
                anyActual + "\n",
                (anyActual + 1) + "\n"
            };
            String[] expectedOutputs = {
                "Introdueix l'any de vigència de la targeta: La targeta ha caducat.\n",
                "Introdueix l'any de vigència de la targeta: La targeta ha caducat.\n",
                "Introdueix l'any de vigència de la targeta: La targeta és vigent.\n",
                "Introdueix l'any de vigència de la targeta: La targeta és vigent.\n",
                "Introdueix l'any de vigència de la targeta: La targeta és vigent.\n",
                "Introdueix l'any de vigència de la targeta: La targeta és vigent.\n"
            };

            for (int i = 0; i < inputs.length; i++) {
                String input = inputs[i];
                String expectedOutput = expectedOutputs[i];

                String actualOutput = SystemLambda.tapSystemOut(() -> {
                    System.setIn(new ByteArrayInputStream(input.getBytes())); // Simula l'entrada de l'usuari
                    Main.main(new String[]{});
                });

                actualOutput = actualOutput.replace("\r\n", "\n"); // Normalitza les noves línies
                String diff = TestStringUtils.findFirstDifference(actualOutput, expectedOutput);
                assertTrue(diff.compareTo("identical") == 0,
                    "\n>>>>>>>>>> >>>>>>>>>>\n" +
                    diff +
                    "<<<<<<<<<< <<<<<<<<<<\n");
            }
        } finally {
            Locale.setDefault(defaultLocale); // Restaura la configuració regional per defecte
        }
    }
}
