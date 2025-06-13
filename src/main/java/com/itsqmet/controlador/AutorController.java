package com.itsqmet.controlador;

import com.itsqmet.entidad.Autor;
import com.itsqmet.entidad.Libro;
import com.itsqmet.servicio.AutorServicio;
import com.itsqmet.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AutorController {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping ("/autores")
    public String listaAutores(@RequestParam (name = "buscarAutor", required = false, defaultValue = "")
                               String buscarAutor, Model model) {
        List<Autor> autores = autorServicio.buscarAutorPorNombre(buscarAutor);
        model.addAttribute("buscarAutor", buscarAutor);
        model.addAttribute("autores", autores);
        return "page/listAutor";
    }

    // Crear Nuevo Autor
    @GetMapping("/formularioAutor")
    public String formularioAutor(Model model) {
        model.addAttribute("autor", new Autor());
        return "page/formularioAutor";
    }

    @PostMapping ("/GuardarAutor")
    public String crearAutor(@ModelAttribute  Autor autor) {
        autorServicio.guardarAutor(autor);
        return "redirect:/autores";
    }

    // Actualizar Autor
    @GetMapping("/editarAutor/{id}")
    public String actualizarAutor(@PathVariable  Long id, Model model) {
        Optional<Autor> autor = autorServicio.bucarAutorPorId(id);
        model.addAttribute("autor", autor.orElse(new Autor()));
        return "page/formularioAutor";
    }

    @GetMapping("/eliminarAutor/{id}")
    public String eliminarAutor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (libroServicio.existenLibrosPorAutor(id)) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el autor porque tiene libros asociados.");
            return "redirect:/autores";
        }

        autorServicio.eliminarAutor(id);
        return "redirect:/autores";
    }


    //Controlador para que me traiga los libros por el id del autor
    @GetMapping("/librosAutor/{id}")
    public String obtenerLibrosPorAutor(@PathVariable Long id, Model model){
        Autor autor = autorServicio.obtenerAutorConLibros(id);
        List<Libro> librosAutor = libroServicio.buscarLibrosAutor(autor.getId());
        model.addAttribute("librosAutor", librosAutor);
        model.addAttribute("autor", autor);
        return "page/listahasAutor";
    }
}
