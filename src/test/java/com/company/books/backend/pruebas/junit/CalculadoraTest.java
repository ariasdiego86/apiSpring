package com.company.books.backend.pruebas.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraTest {

    @Test
    public void calculadoraAssertEqualsTest(){
        Calculadora calculadora = new Calculadora();

        assertEquals(2, calculadora.suma(1,1));
        assertEquals(3, calculadora.resta(4,1));
        assertEquals(18, calculadora.multiplicacion(9, 2));
        assertEquals(10, calculadora.dividir(20, 2));
    }

    @Test
    public void calculadoraTrueFalse()
    {
        Calculadora calculadora = new Calculadora();

        assertTrue(calculadora.suma(1,1) == 2);
        assertTrue(calculadora.resta(1,1) == 0);
        assertTrue(calculadora.multiplicacion(1,1) == 1);
        assertTrue(calculadora.dividir(4,2) == 2);
        assertFalse(calculadora.dividir(4,2) == 0);
    }
}
