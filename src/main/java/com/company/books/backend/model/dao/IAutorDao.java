package com.company.books.backend.model.dao;

import com.company.books.backend.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IAutorDao extends CrudRepository<Autor, Long> {

    // TODO consultas personalizadas

}
