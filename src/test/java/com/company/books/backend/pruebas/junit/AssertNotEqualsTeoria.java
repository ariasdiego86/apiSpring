package com.company.books.backend.pruebas.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AssertNotEqualsTeoria {

    @Test
    public void myTest(){
        assertNotEquals(2, 1);
        //assertNotEquals(2, 2);
    }
}
