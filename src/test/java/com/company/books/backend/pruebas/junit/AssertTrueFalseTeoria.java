package com.company.books.backend.pruebas.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertTrueFalseTeoria {

    @Test
    public void Test1(){
        assertTrue(true);
        assertFalse(false);
    }

    @Test
    public void Test2(){
        boolean expression = 4 == 4;
        boolean expression2 = 4 == 5;

        assertTrue(expression);
        assertFalse(expression2);
    }
}
