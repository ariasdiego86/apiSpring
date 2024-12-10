package com.company.books.backend.service.libro;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.libro.LibroResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

//indicamos que es un servicio
@Service
public class LibroServiceImpl implements ILibroService {

    private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);


    private final ILibroDao libroDao;

    //se inyecta la dependencia
    @Autowired
    public LibroServiceImpl(ILibroDao libroDao) {
        this.libroDao = libroDao;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> getLibros() {

        log.info("inicio de buscarLibros()");

        LibroResponseRest response = new LibroResponseRest();
        List<Libro> libros;

        try{
            libros = (List<Libro>) libroDao.findAll();
            response.getLibroResponse().setLibro(libros);
            response.setMetadata("Respuesta OK", "200", "Respuesta exitora");

        }catch (Exception e){
            log.error(e.getMessage());
            response.setMetadata("Respuesta ERROR", "500", "Error al buscar libros");
            e.getStackTrace();

            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> getLibrosById(Long id) {

        log.info("inicio de getLibroById()");

        LibroResponseRest response = new LibroResponseRest();
        List<Libro> list = new ArrayList<>();

        try{

            Optional <Libro> libro = libroDao.findById(id);

            if(libro.isPresent() ){

                //Codifica la imagen a base64
                /*String imagenBase64 = Base64.getEncoder().encodeToString(libro.get().getImagenBytes());
                libro.get().setImagenBase64(imagenBase64);*/

                list.add(libro.get());
                response.getLibroResponse().setLibro(list);
                response.setMetadata("Respuesta OK", "200", "Respuesta exitosa");
            } else {
                log.error("Error al buscar libro by Id");
                response.setMetadata("Respuesta ERROR", "500", "Libro no encontrado");
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta ERROR", "500", e.getMessage());
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> insertLibro(Libro libro) {

        log.info("inicio de insertLibro()");

        LibroResponseRest response = new LibroResponseRest();
        List <Libro> list = new ArrayList<>();

        try{

            libro.setImagenBytes(libro.getImagen().getBytes());

            Libro libroGuardado = libroDao.save(libro);

            if(libroGuardado != null){
                list.add(libroGuardado);
                response.getLibroResponse().setLibro(list);
                response.setMetadata("Respuesta OK", "200", "Respuesta exitosa, Libro guardado");
            }else{
                log.error("Error al insertar libro");
                response.setMetadata("Respuesta ERROR", "500", "Libro no guardado");
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta ERROR", "500", e.getMessage());
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> updateLibro(Libro libro, Long id) {

        log.info("inicio de updateLibro()");

        LibroResponseRest response = new LibroResponseRest();
        List <Libro> list = new ArrayList<>();

        try{
            Optional<Libro> libroBuscado = libroDao.findById(id);

            if(libroBuscado.isPresent()){
                libroBuscado.get().setAutor(libro.getAutor());
                libroBuscado.get().setDescripcion(libro.getDescripcion());
                libroBuscado.get().setTitulo(libro.getTitulo());
                libroBuscado.get().setCategoria(libro.getCategoria());//attention

                Libro libroActulizado = libroDao.save(libroBuscado.get());

                if(libroActulizado != null){
                    list.add(libroActulizado);
                    response.getLibroResponse().setLibro(list);
                    response.setMetadata("Respuesta OK", "200", "Respuesta exitosa");
                }else {
                    log.error("Error al actualizar libro");
                    response.setMetadata("Respuesta ERROR", "500", "Libro no actualizado");
                    return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                log.error("Libro no encontrado");
                response.setMetadata("Respuesta ERROR", "500", "Libro no encontrado");
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta ERROR", "500", e.getMessage());
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> deleteLibro(Long id) {

        log.info("inicio de deleteLibro()");
        LibroResponseRest response = new LibroResponseRest();

        try {
            libroDao.deleteById(id);
            response.setMetadata("Respuesta OK", "200", "Respuesta exitosa");

        }catch (Exception e) {
            log.error(e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta ERROR", "500", e.getMessage());
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getImagenById(Long id) {
        try {
            // Buscar el libro por ID
            Optional<Libro> libroOpt = libroDao.findById(id);

            if (libroOpt.isPresent()) {
                Libro libro = libroOpt.get();

                if (libro.getImagenBytes() != null) {
                    // Configura los headers para la respuesta
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG); // Cambia si no es JPEG
                    headers.setContentLength(libro.getImagenBytes().length);

                    // Devuelve la imagen como respuesta
                    return new ResponseEntity<>(libro.getImagenBytes(), headers, HttpStatus.OK);
                } else {
                    // Si no tiene imagen, devolver un estado HTTP 204 (sin contenido)
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else {
                // Si el libro no existe, devolver un estado HTTP 404 (no encontrado)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Manejo de excepciones y errores

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
