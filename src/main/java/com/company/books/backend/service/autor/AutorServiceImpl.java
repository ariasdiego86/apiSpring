package com.company.books.backend.service.autor;

import com.company.books.backend.exceptions.ResourceNotFoundException;
import com.company.books.backend.model.Autor;
import com.company.books.backend.model.dao.IAutorDao;
import com.company.books.backend.model.dto.AutorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorServiceImpl implements IAutorService {

    private static final Logger log = LoggerFactory.getLogger(AutorServiceImpl.class);

    private final IAutorDao autorDao;

    @Autowired
    public AutorServiceImpl(IAutorDao autorDao) {
        this.autorDao = autorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<AutorDto>> getAutores() {

        log.info("inicio de getAutores()");

        try {
            List<Autor> autores = (List<Autor>) autorDao.findAll();
            List<AutorDto> autoresDto = autores.stream()
                    .map(this::mapToDto)
                    .toList();
            return ResponseEntity.ok(autoresDto);
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AutorDto> getAutorById(Long id) {
        log.info("Inicio del metodo getAutorById {}", id);
        try {
            Autor autor = autorDao.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + id));
            AutorDto autorDto = mapToDto(autor);
            return ResponseEntity.ok(autorDto);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<AutorDto> insertAutor(AutorDto autorDto) {
        log.info("Inicio del metodo insertAutor {}", autorDto);
        try {
            Autor autor = mapToEntity(autorDto);
            Autor autorGuardado = autorDao.save(autor);
            AutorDto autorGuardadoDto = mapToDto(autorGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(autorGuardadoDto);
        } catch (Exception e) {
            log.error("Error al crear autor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<AutorDto> updateAutor(AutorDto autorDto, Long id) {
        log.info("Inicio del metodo updateAutor {}", id);
        try {
            // Busca el autor por ID
            Autor autor = autorDao.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + id));

            // Actualiza los valores del autor
            autor.setNombre(autorDto.getNombre());
            autor.setApellido(autorDto.getApellido());
            autor.setNacionalidad(autorDto.getNacionalidad());

            // Guarda el autor actualizado
            Autor autorActualizado = autorDao.save(autor);

            // Convierte a DTO y retorna la respuesta
            AutorDto autorActualizadoDto = mapToDto(autorActualizado);
            return ResponseEntity.ok(autorActualizadoDto);
        } catch (ResourceNotFoundException e) {
            log.error("Error al actualizar autor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error inesperado al actualizar autor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<AutorDto> deleteAutor(Long id) {
        log.info("Inicio del metodo deleteAutor {}", id);
        try {
            Autor autor = autorDao.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + id));
            autorDao.delete(autor);
            AutorDto autorDto = mapToDto(autor);
            return ResponseEntity.ok(autorDto);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error al eliminar autor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<AutorDto>> getAutorByNational(String nacionalidad) {
        log.info("Inicio del metodo buscarAutoresPorNacionalidad con nacionalidad: {}", nacionalidad);

        try {
            List<Autor> autores = autorDao.findByNacionalidad(nacionalidad);

            if (autores.isEmpty()) {
                log.info("No se encontraron autores con la nacionalidad: {}", nacionalidad);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            List<AutorDto> autoresDto = autores.stream()
                    .map(this::mapToDto)
                    .toList();

            return ResponseEntity.ok(autoresDto);

        } catch (Exception e) {
            log.error("Error inesperado al buscar autores por nacionalidad: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private AutorDto mapToDto(Autor autor) {
        AutorDto dto = new AutorDto();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setApellido(autor.getApellido());
        dto.setNacionalidad(autor.getNacionalidad());
        return dto;
    }

    private Autor mapToEntity(AutorDto dto) {
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setNacionalidad(dto.getNacionalidad());
        return autor;
    }

}
