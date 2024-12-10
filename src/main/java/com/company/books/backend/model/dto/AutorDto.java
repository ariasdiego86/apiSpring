package com.company.books.backend.model.dto;

import lombok.Data;

@Data
public class AutorDto {

    private long id;
    private String nombre;
    private String apellido;
    private String nacionalidad;

    //TODO aca no se pone el campo password si lo tuvieramos

}
