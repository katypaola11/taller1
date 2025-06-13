package com.itsqmet.servicio;

import com.itsqmet.entidad.Autor;
import com.itsqmet.repositorio.AutorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;

    //Metodos para mostrar los Autor

    public List<Autor> mostrarAutor(){
        return autorRepositorio.findAll(); // metodo para mostrar todos los Autor  //findAll es del JPARepository // clase herencia
    }


    //Buscar Autor por ID
    public Optional<Autor> bucarAutorPorId(Long id){
        return autorRepositorio.findById(id);
    }
    //Metodo para buscar los autor por titulo
    public List<Autor> buscarAutorPorNombre(String buscarAutor) {
        if (buscarAutor == null || buscarAutor.isEmpty()) {
            return autorRepositorio.findAll();
        }
        return autorRepositorio.findByNombreContainingIgnoreCase(buscarAutor);
    }

    //guardar Autor
    public Autor guardarAutor(Autor autor){
        return autorRepositorio.save(autor);
    }

    //Eliminar el Autor

    public void eliminarAutor(Long id){
        autorRepositorio.deleteById(id);
    }


    //Metodp para obtener el nombre del autoe con ls libros
    @Transactional
    public Autor obtenerAutorConLibros(Long id){
        //Aqui es una manera mas simple para el manejo de excepciones
        Autor autor = autorRepositorio.findById(id).orElseThrow();
        return autor;
    }

}
