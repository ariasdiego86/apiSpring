package com.company.books.backend.service.autor;

import com.company.books.backend.model.dto.AutorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAutorService {

    public ResponseEntity<List<AutorDto>> getAutores();

    public ResponseEntity<AutorDto> getAutorById(Long id);

    public ResponseEntity<AutorDto> insertAutor(AutorDto request);

    public ResponseEntity<AutorDto> updateAutor(AutorDto request, Long id);

    public ResponseEntity<AutorDto> deleteAutor(Long id);

    public ResponseEntity<List<AutorDto>> getAutorByNational(String nacionalidad);

}
