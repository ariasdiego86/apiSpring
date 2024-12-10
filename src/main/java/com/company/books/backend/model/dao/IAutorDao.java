package com.company.books.backend.model.dao;

import com.company.books.backend.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAutorDao extends CrudRepository<Autor, Long> {

    // TODO consultas personalizadas
    @Query("SELECT a FROM Autor a WHERE a.nacionalidad = :nacionalidad")
    List<Autor> findByNacionalidad(@Param("nacionalidad") String nacionalidad);

}
