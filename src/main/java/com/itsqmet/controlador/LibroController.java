package com.itsqmet.controlador;

import com.itsqmet.entidad.Autor;
import com.itsqmet.entidad.Libro;
import com.itsqmet.servicio.AutorServicio;
import com.itsqmet.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller

public class LibroController {
    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    //leer los libros
    @GetMapping ("/libros")
    public String listarLibros(@RequestParam (name = "buscarLibro", required = false, defaultValue = "")
                               String buscarLibro, Model model){
        List<Libro> libros = libroServicio.buscarLibroPorTitulo(buscarLibro);
        model.addAttribute("buscarLibro", buscarLibro);
        model.addAttribute("libros", libros);
        return "page/listLibro";
    }

    //Aqui se puede hacer la extraccion en la base de datos
    //Crear Nuevo Libro
    @GetMapping("/formularioLibro")
    public String formularioLibro(Model model){
        model.addAttribute("libro", new Libro());
        //Paso de autores desde el servicio autor a formulario
        List<Autor> autores = autorServicio.mostrarAutor();
        model.addAttribute("autores", autores);
        return "page/formularioLibro";

    }

    @PostMapping ("/guardarLibro")
    public String crearLibro(Libro libro){
        libroServicio.guardarLibro(libro);
        return  "redirect:/libros";

    }

    //Actualizar libro
    @GetMapping("/editarLibro/{id}")
    public String actualizarLibro(@PathVariable  Long id,
                                  Model model){
        Optional<Libro> libro = libroServicio.bucarLibroPorId(id);
        //Paso de autores desde el servicio autor a formulario
        model.addAttribute("autores", autorServicio.mostrarAutor());
        model.addAttribute("libro", libro);
        return "page/formularioLibro";

    }

    @GetMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable Long id){
        libroServicio.eliminarLibro(id);
        return "redirect:/libros";

    }
}
