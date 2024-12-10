package com.company.books.backend.controllers;

import com.company.books.backend.model.dto.AutorDto;
import com.company.books.backend.service.autor.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class AutorRestController {

    private final IAutorService service;

    @Autowired
    public AutorRestController(IAutorService service) {
       this.service = service;
    }

    @GetMapping("/autores")
    public ResponseEntity<List<AutorDto>> getAllAutores(){
        return service.getAutores();
    }

    @GetMapping("/autores/{id}")
    public ResponseEntity<AutorDto> getAutorById(@PathVariable Long id){
        return service.getAutorById(id);
    }

    @PostMapping("/autores")
    public ResponseEntity<AutorDto> createAutor(@RequestBody AutorDto dto){
        return service.insertAutor(dto);
    }

    @PutMapping("/autores/{id}")
    public ResponseEntity<AutorDto> updateAutor(@RequestBody AutorDto dto, @PathVariable Long id){
        return service.updateAutor(dto, id);
    }

    @DeleteMapping("/autores/{id}")
    public ResponseEntity<AutorDto> deleteAutor(@PathVariable Long id){
        return service.deleteAutor(id);
    }

    @GetMapping("/autores/nacionalidad/{nacionalidad}")
    public ResponseEntity<List<AutorDto>> getAutoresByNational(@PathVariable String nacionalidad){
        return service.getAutorByNational(nacionalidad);
    }
}
