package com.company.books.backend.pruebas.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AssertArrayEquals {

    @Test
    public void equalsArrayTest(){
        String [] array = {"A","B","C","D","E","F"};
        String [] array2 = {"A","B","C","D","E","F"};
        String [] array3 = {"A","B","C","D","E", "G"};

        assertArrayEquals(array,array2);
        assertArrayEquals(array,array3);// falla si hay diferente longitud o diferentes caracteres
    }
}
