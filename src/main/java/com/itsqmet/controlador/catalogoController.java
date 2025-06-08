package com.itsqmet.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class catalogoController {
    @GetMapping ("/catalogo")
    public String mostrarCatalogo(){
        return ("pages/catalogo");
    }
}
