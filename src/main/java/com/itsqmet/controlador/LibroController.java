package com.itsqmet.controlador;

import com.itsqmet.entidad.Autor;
import com.itsqmet.entidad.Libro;
import com.itsqmet.servicio.AutorServicio;
import com.itsqmet.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.StandardCopyOption;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

import java.io.IOException;



import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/libros")

public class LibroController {
    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;


    @GetMapping
    public String redireccionarLibros() {
        return "redirect:/libros/librosT";
    }


    //leer los libros
    @GetMapping ("/librosT")
    public String listarLibros(@RequestParam (name = "buscarLibro", required = false, defaultValue = "")
                               String buscarLibro, Model model){
        List<Libro> libros = libroServicio.buscarLibroPorTitulo(buscarLibro);
        model.addAttribute("buscarLibro", buscarLibro);
        model.addAttribute("libros", libros);
        return "page/listLibro";
    }

    //Aqui se puede hacer la extraccion en la base de datos
    //Crear Nuevo Libro
    @GetMapping("page/formularioLibro")
    public String formularioLibro(Model model){
        model.addAttribute("libro", new Libro());
        //Paso de autores desde el servicio autor a formulario
        List<Autor> autores = autorServicio.mostrarAutor();
        model.addAttribute("autores", autores);
        return "page/formularioLibro";

    }

    @PostMapping("/guardarLibro")
    public String guardarLibro(@ModelAttribute Libro libro,
                               @RequestParam("archivo") MultipartFile archivo) {
        if (!archivo.isEmpty()) {
            String nombreArchivo = archivo.getOriginalFilename(); // nombre del PDF subido
            Path ruta = Paths.get(rutaBase, nombreArchivo);
            try {
                Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
                libro.setArchivoPdf("archivoPdf"); // guardamos el nombre del archivo PDF en la entidad
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        libroServicio.guardarLibro(libro); // guarda libro con título y nombre de archivo
        return "redirect:/libros";
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

    // Endpoint para leer PDF en línea
    private final String rutaBase = "C:\\Users\\ITSQMET ESTUDIANTES\\Desktop\\PROYECTO\\librosPdf";

    @GetMapping("/view/{id}")
    @ResponseBody
    public ResponseEntity<Resource> verPdf(@PathVariable Long id) {
        // Obtener libro por id, obtener nombre de archivo, etc.
        String nombreArchivo = "donQuijote.pdf"; // ejemplo estático

        File archivo = new File(rutaBase, nombreArchivo);
        if (!archivo.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(archivo);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nombreArchivo + "\"")
                .body(resource);
    }


    // Endpoint para descargar PDF
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> descargarPdf(@PathVariable Long id) {
        try {
            Libro libro = libroServicio.obtenerPorId(id);

            // Ruta donde están almacenados los PDFs
            String rutaPdf = "uploads/pdfs/" + libro.getArchivoPdf();
            Path path = Paths.get(rutaPdf);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + libro.getTitulo() + ".pdf\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para obtener estadísticas de un libro específico
    @GetMapping("/estadisticas/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasLibro(@PathVariable Long id) {
        try {
            Libro libro = libroServicio.obtenerPorId(id);
            Map<String, Object> estadisticas = new HashMap<>();
            estadisticas.put("titulo", libro.getTitulo());
            estadisticas.put("visualizaciones", libro.getContadorVisualizaciones());
            estadisticas.put("descargas", libro.getContadorDescargas());
            estadisticas.put("totalInteracciones", libro.getContadorVisualizaciones() + libro.getContadorDescargas());

            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para obtener estadísticas generales de todos los libros
    @GetMapping("/estadisticas/general")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasGenerales() {
        try {
            List<Libro> todosLosLibros = libroServicio.obtenerTodos();

            int totalVisualizaciones = todosLosLibros.stream()
                    .mapToInt(libro -> libro.getContadorVisualizaciones() != null ? libro.getContadorVisualizaciones() : 0)
                    .sum();

            int totalDescargas = todosLosLibros.stream()
                    .mapToInt(libro -> libro.getContadorDescargas() != null ? libro.getContadorDescargas() : 0)
                    .sum();

            Map<String, Object> estadisticas = new HashMap<>();
            estadisticas.put("totalVisualizaciones", totalVisualizaciones);
            estadisticas.put("totalDescargas", totalDescargas);
            estadisticas.put("totalInteracciones", totalVisualizaciones + totalDescargas);
            estadisticas.put("totalLibros", todosLosLibros.size());

            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para mostrar página de estadísticas
    @GetMapping("/estadisticas")
    public String mostrarEstadisticas(Model model) {
        List<Libro> libros = libroServicio.obtenerTodos();
        model.addAttribute("libros", libros);
        return "page/estadisticas"; // Crear esta vista
    }


}
