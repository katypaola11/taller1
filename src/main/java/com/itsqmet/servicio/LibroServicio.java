package com.itsqmet.servicio;

import com.itsqmet.entidad.Libro;
import com.itsqmet.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    //Metodos para mostrar los libros

    public List<Libro> mostrarLibros(){
        return libroRepositorio.findAll(); // metodo para mostrar todos los libros  //findAll es del JPARepository // clase herencia
    }

    //Metodo para buscar los libros por titulo
    public List<Libro> buscarLibroPorTitulo(String buscarLibro){
        if (buscarLibro==null || buscarLibro.isEmpty()){
            return libroRepositorio.findAll();
        }else{
            return libroRepositorio.findByTituloContainingIgnoreCase(buscarLibro);
        }

    }

    //Buscar libro por ID
    public Optional<Libro> bucarLibroPorId(Long id){
        return libroRepositorio.findById(id);
    }
    //guardar Libro
    public Libro guardarLibro(Libro libro){
        return libroRepositorio.save(libro);
    }

    //Eliminar el libro
    public void eliminarLibro(Long id){
        libroRepositorio.deleteById(id);
    }

    //Buscar los libros con autores
    public List<Libro> buscarLibrosAutor (Long id){
        List<Libro> librosAutor = libroRepositorio.findByAutorId(id);
        return librosAutor;
    }

    public boolean existenLibrosPorAutor(Long autorId) {
        return !libroRepositorio.findByAutorId(autorId).isEmpty();
    }
}
